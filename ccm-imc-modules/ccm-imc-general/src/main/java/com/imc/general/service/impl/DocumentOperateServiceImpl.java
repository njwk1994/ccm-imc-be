package com.imc.general.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.file.base.UploadedFile;
import com.imc.common.core.model.parameters.UserEnvParameter;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.utils.file.FileTypeUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.constant.*;
import com.imc.framework.context.Context;
import com.imc.framework.model.IInterface;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.general.service.IDocumentOperateService;
import com.imc.schema.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DocumentOperateServiceImpl implements IDocumentOperateService {

    /**
     * 附加文件
     * Chen  Jing
     *
     * @param files      文件
     * @param pstrDocUid 文档唯一标识
     * @return boolean
     */
    @Override
    public boolean attachFiles(@NotNull MultipartFile[] files, String pstrDocUid) throws Exception {
        if (StringUtils.isEmpty(pstrDocUid)) {
            throw new Exception("未获取到有效的文档唯一标识!");
        }
        //查询文档
        ICIMDocumentMaster lobjDocMaster = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(pstrDocUid, ClassDefConstant.CIM_DESIGN_DOC_MASTER, ICIMDocumentMaster.class);
        Objects.requireNonNull(lobjDocMaster, HandlerExceptionUtils.objNotFoundFailMsg(pstrDocUid, ClassDefConstant.CIM_DESIGN_DOC_MASTER));
        //获取到最新的version
        ICIMDocumentRevision lobjNewestRevision = lobjDocMaster.getNewestRevision();
        Objects.requireNonNull(lobjNewestRevision, "未获取到当前文档最新的版本信息!");
        ICIMDocumentVersion lobjNewestDocumentVersion = lobjNewestRevision.getNewestDocumentVersion();
        Objects.requireNonNull(lobjNewestDocumentVersion, "未获取到当前文档最新的修订信息!");
        ICIMFileComposition lobjFileComposition = lobjNewestDocumentVersion.toInterface(ICIMFileComposition.class);
        Objects.requireNonNull(lobjFileComposition, HandlerExceptionUtils.convertInterfaceFailMsg(InterfaceDefConstant.IMC_FILE_COMPOSITION));
        ObjectCollection lobjTransaction = new ObjectCollection(new UserEnvParameter(GeneralUtil.getUsername()));
        //获取文档的分类目录层级结构
        String lstrCategoryLevelPath = lobjDocMaster.getCategoryLevelPath();
        //先获取已经存在的File
        List<IObject> lcolFiles = lobjFileComposition.getFiles();
        int lintFileSize = CollectionUtils.hasValue(lcolFiles) ? lcolFiles.size() : 0;
        for (MultipartFile multipartFile : files) {
            String lstrOriginalFilename = multipartFile.getOriginalFilename();
            if (StringUtils.isEmpty(lstrOriginalFilename)) throw new Exception("无效的文件名");
            String lstrFileNameWithoutExt = FilenameUtils.getBaseName(lstrOriginalFilename);
            String lstrExt = FileTypeUtils.getExtension(multipartFile);
            IObject lobjPointedFile = null;
            if (CollectionUtils.hasValue(lcolFiles)) {
                lobjPointedFile = lcolFiles.stream().filter(r -> r.getName().equalsIgnoreCase(lstrOriginalFilename)).findFirst().orElse(null);
            }
            if (lobjPointedFile != null) {
                //将原来删除,重新上传
                ICIMFile lobjCIMFile = lobjPointedFile.toInterface(ICIMFile.class);
                Objects.requireNonNull(lobjCIMFile, HandlerExceptionUtils.convertInterfaceFailMsg(InterfaceDefConstant.IMC_FILE));
                lobjPointedFile.BeginUpdate(lobjTransaction);
                ICIMPhysicalFile physicalFile = lobjPointedFile.toInterface(ICIMPhysicalFile.class);
                List<JSONObject> fileSaveInfo = physicalFile.getFileSaveInfo();
                //删除原来的文件
                Context.Instance.getFileOperateHelper().deletePhysicalFilesFromFileServer(fileSaveInfo);
                UploadedFile uploadedFile = Context.Instance.getFileOperateHelper().uploadFile(multipartFile, lstrCategoryLevelPath);
                if (null != uploadedFile) {
                    physicalFile.setFileSaveInfo(uploadedFile);
                    physicalFile.setFileBaseInfo(uploadedFile);
                    lobjPointedFile.FinishUpdate(lobjTransaction);
                }
            } else {
                lintFileSize++;
                //直接创建File
                IObject lobjNewFile = SchemaUtil.newIObject(ClassDefConstant.CIM_FILE, lstrOriginalFilename, "", lstrOriginalFilename);
                Objects.requireNonNull(lobjNewFile, HandlerExceptionUtils.createObjFailMsg(ClassDefConstant.CIM_FILE));
                ICIMFile lobjCIMFile = lobjNewFile.toInterface(ICIMFile.class);
                Objects.requireNonNull(lobjCIMFile, HandlerExceptionUtils.convertInterfaceFailMsg(InterfaceDefConstant.IMC_FILE));
                //上传文件到minio
                UploadedFile uploadedFile = Context.Instance.getFileOperateHelper().uploadFile(multipartFile, lstrCategoryLevelPath);
                if (null != uploadedFile) {
                    IInterface lobjInterface = lobjCIMFile.addInterface(InterfaceDefConstant.PHYSICAL_FILE);
                    Objects.requireNonNull(lobjInterface, HandlerExceptionUtils.convertInterfaceFailMsg(InterfaceDefConstant.PHYSICAL_FILE));
                    ICIMPhysicalFile physicalFile = lobjCIMFile.toInterface(ICIMPhysicalFile.class);
                    physicalFile.setFileSaveInfo(uploadedFile);
                    physicalFile.setFileBaseInfo(uploadedFile);
                    lobjCIMFile.finishCreate(lobjTransaction);
                    //创建与version的关联
                    IRel newRel = SchemaUtil.newRelationship(RelDefConstant.FILE_COMPOSITIONS, lobjCIMFile, lobjFileComposition);
                    newRel.finishCreate(lobjTransaction);
                    //创建与FileType的关联
                    //1.先获取FileType
                    IObject lobjFileType = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(GeneralConstant.UID_PREFIX_FILE_TYPE + (lstrExt.toLowerCase()), ClassDefConstant.CIM_FILE_TYPE, IObject.class);
                    if (lobjFileType == null) {
                        lobjFileType = SchemaUtil.getDefaultFileType();
                        if (lobjFileType == null) {
                            throw new Exception("未找到默认的文件类型对象!");
                        }
                    }
                    //2.创建关联
                    IRel lobjFileFileType = SchemaUtil.newRelationship(RelDefConstant.FILE_FILE_TYPE, lobjCIMFile, lobjFileType);
                    lobjFileFileType.finishCreate(lobjTransaction);
                } else {
                    throw new Exception("上传至minio失败");
                }
            }
        }
        //更新文件数量
        lobjFileComposition.BeginUpdate(lobjTransaction);
        lobjFileComposition.setFileCount(lintFileSize);
        lobjFileComposition.FinishUpdate(lobjTransaction);
        //提交
        lobjTransaction.commit();
        return true;
    }

    /**
     * 得到文档文件
     * Chen  Jing
     *
     * @param pstrDocUid pstr doc uid
     * @return {@code TableData<CIMFileModel> }
     */
    @Override
    public TableData<JSONObject> getDocumentFiles(@NotNull String pstrDocUid) throws Exception {
        if (StringUtils.isEmpty(pstrDocUid)) throw new Exception("文档唯一标识不能为空值");
        ICIMDocumentMaster lobjDocMaster = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(pstrDocUid, ClassDefConstant.CIM_DESIGN_DOC_MASTER, ICIMDocumentMaster.class);
        Objects.requireNonNull(lobjDocMaster, HandlerExceptionUtils.objNotFoundFailMsg(pstrDocUid, ClassDefConstant.CIM_DESIGN_DOC_MASTER));
        ICIMDocumentVersion lobjNewestVersion = lobjDocMaster.getNewestVersion();
        Objects.requireNonNull(lobjNewestVersion, "未找到文档的最新修订信息!");
        ICIMFileComposition lobjFileComposition = lobjNewestVersion.toInterface(ICIMFileComposition.class);
        Objects.requireNonNull(lobjFileComposition, HandlerExceptionUtils.convertInterfaceFailMsg(InterfaceDefConstant.IMC_FILE_COMPOSITION));
        List<IObject> lcolFiles = lobjFileComposition.getFiles();
        TableData<JSONObject> tableData = GeneralUtil.generateTableData(lcolFiles, GeneralUtil.getUsername(), ClassDefConstant.CIM_FILE);
        tableData.disableAddAndRefresh(false);
        tableData.switchSelectionModeSingleOrMulti(false);
        return tableData;
    }

    /**
     * 下载选中文件
     * Chen  Jing
     *
     * @param pstrFileUids uid pstr文件
     * @param response     响应
     */
    @Override
    public void downloadSelectedFiles(@NotEmpty String pstrFileUids, @NotNull HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(pstrFileUids)) {
            return;
        }
        List<IObject> files = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(Arrays.asList(pstrFileUids.split(",")), ClassDefConstant.CIM_FILE, IObject.class);
        if (!CollectionUtils.hasValue(files)) {
            HandlerExceptionUtils.throwObjNotFoundException(ClassDefConstant.CIM_FILE, pstrFileUids);
        }
        downloadFiles(response, files);
    }

    /**
     * 下载文档文件
     * Chen  Jing
     *
     * @param pstrDocUid 文档唯一标识
     * @param response   响应
     */
    @Override
    public void downloadDocFiles(@NotEmpty String pstrDocUid, @NotNull HttpServletResponse response) throws Exception {
        ICIMDocumentMaster lobjDocMaster = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(pstrDocUid, ClassDefConstant.CIM_DESIGN_DOC_MASTER, ICIMDocumentMaster.class);
        Objects.requireNonNull(lobjDocMaster, HandlerExceptionUtils.objNotFoundFailMsg(pstrDocUid, ClassDefConstant.CIM_DESIGN_DOC_MASTER));
        ICIMDocumentVersion lobjNewestVersion = lobjDocMaster.getNewestVersion();
        Objects.requireNonNull(lobjNewestVersion, "未找到有效的修订信息!");
        List<IObject> lcolFiles = lobjNewestVersion.getAllFiles();
        if (CollectionUtils.hasValue(lcolFiles)) {
            downloadFiles(response, lcolFiles);
        } else {
            response.getOutputStream().close();
            throw new Exception("该文档没有物理文件可供下载!");
        }
    }

    private static void downloadFiles(@NotNull HttpServletResponse response, List<IObject> files) throws Exception {
        InputStream inputStream = Context.Instance.getFileOperateHelper().downloadFiles(files, GeneralUtil.getUsername());
        if (inputStream != null) {
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copyLarge(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        } else {
            response.getOutputStream().close();
            throw new Exception("下载失败!");
        }
    }
}
