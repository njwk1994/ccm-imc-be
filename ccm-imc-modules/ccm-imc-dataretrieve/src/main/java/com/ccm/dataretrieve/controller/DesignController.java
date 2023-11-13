package com.ccm.dataretrieve.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.dataretrieve.entity.ConstructionType;
import com.ccm.dataretrieve.service.IDesignService;
import com.ccm.dataretrieve.vo.ConstructionTypeSearchVo;
import com.ccm.dataretrieve.vo.ExportDocumentWeldVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.imc.common.core.domain.R;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.MessageUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.schema.interfaces.IObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 设计数据对象 控制层
 */
@RestController
@Slf4j
@RequestMapping("/design")
@Api(tags = "设计数据")
public class DesignController {

    private final IDesignService designService;

    public DesignController(IDesignService designService) {
        this.designService = designService;
    }


    /**
     * 获取图纸对应的施工分类
     *
     * @return
     * @throws Exception
     */
    @ApiOperation("获取图纸对应的施工分类")
    @PostMapping("/getDocumentConstructionTypes")
    @ApiOperationSupport(params = @DynamicParameters(name = "ConstructionTypeSearchVo", properties = {
            @DynamicParameter(name = "uid", value = "图纸UID", example = "图纸UID", required = true, dataTypeClass = String.class),
    }))
    public R<List<ConstructionType>> getDocumentConstructionTypes(@RequestBody ConstructionTypeSearchVo constructionTypeSearchVo) throws Exception {
        try {
            return R.ok(designService.getDocumentConstructionTypes(constructionTypeSearchVo));
        } catch (Exception ex) {
            log.error("-----获取图纸对应的施工分类失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    /**
     * 获取图纸对应的施工分类
     *
     * @return
     * @throws Exception
     */
    @ApiOperation("导出图纸焊口数据")
    @PostMapping("/exportDocumentWeld")
    @ApiOperationSupport(params = @DynamicParameters(name = "ConstructionTypeSearchVo", properties = {
            @DynamicParameter(name = "type", value = "模版类型(DD-详设,SD-加设)", example = "DD/SD)", required = true, dataTypeClass = String.class),
    }))
    public R exportDocumentWeld(@RequestBody ExportDocumentWeldVo exportDocumentWeldVo, HttpServletResponse response) throws Exception {
        try {
            this.designService.exportDocumentWeld(exportDocumentWeldVo, response);
            response.getOutputStream().close();
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----导出图纸焊口数据失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }



}
