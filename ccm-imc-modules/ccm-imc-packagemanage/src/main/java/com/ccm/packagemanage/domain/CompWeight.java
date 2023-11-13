package com.ccm.packagemanage.domain;

import lombok.Data;

@Data
public class CompWeight {
    private String compOBID;
    private Double weight;

    public CompWeight(String compOBID, Double weight) {
        this.compOBID = compOBID;
        this.weight = weight;
    }
}
