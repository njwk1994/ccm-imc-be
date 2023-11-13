package com.ccm.packagemanage.domain;


import com.imc.schema.interfaces.IObject;
import lombok.Data;

@Data
public class DocumentPriorityWeightDTO {
    private IObject document;
    private Double weight;

    public DocumentPriorityWeightDTO(IObject document, Double weight) {
        this.document = document;
        this.weight = weight;
    }
}
