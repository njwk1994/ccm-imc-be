package com.imc.general.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.model.method.ClientApiVO;
import com.imc.common.core.model.parameters.*;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.MessageUtils;
import com.imc.common.core.web.comp.OptionItem;
import com.imc.common.core.web.form.FormDataInfo;
import com.imc.common.core.web.option.SelectionOption;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.tree.TreeNode;
import com.imc.framework.excel.entity.ExcelDetail;
import com.imc.framework.excel.entity.SheetDetail;
import com.imc.framework.excel.writeHandler.AutoAdjustWidthHeigtStyleStrategy;
import com.imc.general.service.IGeneralService;
import com.imc.general.vo.CreateRelVo;
import com.imc.general.vo.QueryHierarchyObjectsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


@RestController
@Api(tags = "IMC通用统一的前台交互")
@Slf4j
@RequestMapping("/generalAction")
public class GeneralController {

    private final IGeneralService generalService;

    public GeneralController(IGeneralService generalService) {
        this.generalService = generalService;
    }

    @ApiOperation("导入Schema文件(JSON | Xml)")
    @PostMapping("/loadSchemaFiles")
    public R<String> loadSchemaFiles(@RequestPart(value = "files") MultipartFile[] files) {
        try {
            this.generalService.loadSchemaFiles(files);
            return R.ok(MessageUtils.get("import.success"));
        } catch (Exception ex) {
            log.error("-------------导入Schema失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @GetMapping("/querySelectOptionByInterfaceDefUid/{interfaceDefUid}")
    @ApiOperation("根据接口定义查询对象下拉列表")
    public R<SelectionOption> querySelectOptionByInterfaceDefUid(@PathVariable String interfaceDefUid) {
        try {
            return R.ok(this.generalService.querySelectOptionByInterfaceDefUid(interfaceDefUid));
        } catch (Exception ex) {
            log.error("-------------根据接口定义查询对象下拉列表----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("通过类型定义及层级查询层级类对象指定层级对象列表")
    @PostMapping("/queryHierarchyObjectsByClassDefAndLevel")
    public R<List<TreeNode>> queryHierarchyObjectsByClassDefAndLevel(@RequestBody QueryHierarchyObjectsVo hierarchyObjectsVo) {
        try {
            return R.ok(this.generalService.queryHierarchyObjectsByClassDefAndLevel(hierarchyObjectsVo));
        } catch (Exception ex) {
            log.error("-------------通过类型定义及层级查询层级类对象列表失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("查询下拉树指定节点对象的子集信息")
    @PostMapping("/querySelectTreeNodeChildrenByUidAndClassDef")
    public R<List<OptionItem>> querySelectTreeNodeChildrenByUidAndClassDef(@RequestBody ObjParam objParam) {
        try {
            return R.ok(this.generalService.querySelectTreeNodeChildrenByUidAndClassDef(objParam));
        } catch (Exception ex) {
            log.error("-------------查询下拉树指定节点对象的子集信息失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("通用查询")
    @PostMapping("/generalQuery")
    public R<TableData<JSONObject>> generalQuery(@RequestBody GeneralQueryParam queryParam) {
        try {
            return R.ok(this.generalService.generalQuery(queryParam));
        } catch (Exception ex) {
            log.error("------------查询失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取表单")
    @PostMapping("/getForm")
    public R<FormDataInfo> getForm(@RequestBody QueryFormParam formParam) {
        try {
            return R.ok(this.generalService.getForm(formParam));
        } catch (Exception ex) {
            log.error("------------获取表单失败-----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("通用更新对象")
    @PostMapping("/generalUpdate")
    public R<Boolean> generalUpdate(@RequestBody JSONObject jsonObject) {
        try {
            return R.ok(this.generalService.generalUpdate(jsonObject), MessageUtils.get("update.success"));
        } catch (Exception ex) {
            log.error("-----------更新对象失败-------------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("查询层级对象结构")
    @PostMapping("/queryHierarchyObjectByClassDefUid")
    public R<List<TreeNode>> queryHierarchyObjectByClassDefUid(@RequestBody QueryHierarchyObjectsVo hierarchyObjectsVo) {
        try {
            return R.ok(this.generalService.queryHierarchyObjectByClassDefUid(hierarchyObjectsVo));
        } catch (Exception ex) {
            log.error("-----------查询层级对象结构失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("获取对象可用的方法列表")
    @PostMapping("/getObjectsMethods")
    public R<List<ClientApiVO>> getObjectsMethods(@RequestBody ObjParam objParam) {
        try {
            return R.ok(this.generalService.getObjectsMethods(objParam));
        } catch (Exception ex) {
            log.error("-----------获取对象可用的方法列表失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("根据类型定义获取可用的方法")
    @PostMapping("/getMethodsByClassDef")
    public R<List<ClientApiVO>> getMethodsByClassDef(@RequestBody JSONObject param) {
        try {
            return R.ok(this.generalService.getMethodsByClassDef(param));
        } catch (Exception ex) {
            log.error("-----------根据类型定义获取可用的方法失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("通用创建")
    @PostMapping("/generaCreate")
    public R<Boolean> generaCreate(@RequestBody JSONObject jsonObject) {
        try {
            return R.ok(this.generalService.generalCreate(jsonObject), MessageUtils.get("create.success"));
        } catch (Exception ex) {
            log.error("-----------创建对象失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("创建关联关系")
    @PostMapping("/createRelationship")
    public R<Boolean> createRelationship(@RequestBody CreateRelVo createRelVo) {
        try {
            return R.ok(this.generalService.createRelationship(createRelVo), MessageUtils.get("create.success"));
        } catch (Exception ex) {
            log.error("-----------创建关联关系失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("查询对象所有关联关系实例")
    @PostMapping("/queryObjAllRelationships")
    public R<TableData<JSONObject>> queryObjAllRelationships(@RequestBody ObjParam param) {
        try {
            return R.ok(this.generalService.queryObjAllRelationships(param), MessageUtils.get("query.success"));
        } catch (Exception ex) {
            log.error("-----------查询对象所有关联关系实例失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("通用删除")
    @PostMapping("/generalDelete")
    public R<Boolean> generalDelete(@RequestBody DeleteParam deleteParam) {
        try {
            return R.ok(this.generalService.deleteObjects(deleteParam), MessageUtils.get("delete.success"));
        } catch (Exception ex) {
            log.error("------------删除对象失败-----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("挂载附件")
    @PostMapping("/attachFiles")
    public R<Boolean> attachFiles(@RequestBody AttachFileParam attachFileParam) {
        try {
            return R.ok(this.generalService.attachFiles(attachFileParam));
        } catch (Exception ex) {
            log.error("------------挂载附件失败-----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("获取枚举的Enums")
    @PostMapping("/getEnumListEntries")
    @ApiImplicitParam(name = "EnumListType的Uid", value = "uid", dataTypeClass = String.class, paramType = "body", required = true)
    public R<SelectionOption> getEnumListEntries(@RequestBody JSONObject pobjParam) {
        try {
            return R.ok(this.generalService.getEnumListEntries(pobjParam.getString("uid")), MessageUtils.get("query.success"));
        } catch (Exception ex) {
            log.error("------------获取枚举的Enums失败!-----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("通用查询导出Excel")
    @PostMapping("/generalExportExcel")
    public void generalExportExcel(@RequestBody GeneralQueryParam queryParam, HttpServletResponse response) {
        try (ExcelWriter excelWriter = EasyExcelFactory.write(response.getOutputStream()).build()) {
            ExcelDetail excelDetail = generalService.generalExportExcel(queryParam);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // URLEncoder.encode防止中文乱码
            String fileName = URLEncoder.encode("export_" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<SheetDetail> sheetDetails = excelDetail.getSheetDetails();
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            contentWriteCellStyle.setWrapped(true);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
            for (int i = 0; i < sheetDetails.size(); i++) {
                SheetDetail sheetDetail = sheetDetails.get(i);
                WriteSheet writeSheet =
                        EasyExcelFactory
                                .writerSheet(i, sheetDetail.getSheetName())
                                .registerWriteHandler(new AutoAdjustWidthHeigtStyleStrategy())
                                .registerWriteHandler(horizontalCellStyleStrategy)
                                .head(sheetDetail.getHeaders()).build();
                excelWriter.write(sheetDetail.getData(), writeSheet);
            }
        } catch (Exception e) {
            log.error("------------通用查询导出Excel失败----------", e);
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            try {
                response.getWriter().println(JSON.toJSONString(R.fail(ExceptionUtil.getRootErrorMessage(e))));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
