package com.imc.general.vo;

import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class QueryHierarchyObjectsVo implements Serializable {

    private static final Long serializableId = 1L;

    @ApiModelProperty("要查询的对象的类型定义")
    private String classDefUid;

    @ApiModelProperty("过滤用的对象名称")
    private String name;

    @ApiModelProperty("层级对象的层级信息")
    private Integer level = null;

    public void validateParam() throws Exception {
        if (StringUtils.isBlank(classDefUid)) {
            HandlerExceptionUtils.throwParamsCanNotBeNull("classDefUid");
        }
    }

    /**
     * CHEN JING
     * 2023/07/04 20:58:54
     * 是否有名称过滤值
     *
     * @return boolean
     */
    public boolean hasNameCriteria() {
        if (StringUtils.isBlank(this.name)) {
            return false;
        }
        return !"*".equalsIgnoreCase(this.name);
    }
}
