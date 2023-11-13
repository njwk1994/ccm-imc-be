package com.ccm.packagemanage.domain;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 14:50
 * @PackageName:com.ccm.packagemanage.domain
 * @ClassName: ProcedureResult
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class ProcedureResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectName;

    private Integer requestId = 0;
    private Integer result;
    private String requestIdStr;
    private String requestDate;
    private String message = "";
    private int exist = 0;
    private JSONArray requestDataCursor;

    public boolean isExist() {
        return this.exist > 0;
    }
}
