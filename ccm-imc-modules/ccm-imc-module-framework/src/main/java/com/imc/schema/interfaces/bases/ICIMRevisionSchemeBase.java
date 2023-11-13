package com.imc.schema.interfaces.bases;

import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMRevisionScheme;

public abstract class ICIMRevisionSchemeBase  extends InterfaceDefault implements ICIMRevisionScheme {


    public ICIMRevisionSchemeBase(boolean instantiateRequiredProperties) {
        super(ICIMRevisionScheme.class.getSimpleName(),instantiateRequiredProperties);
    }


    @Override
    public String getCIMMajorSequence() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequence");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMajorSequence(String CIMMajorSequence) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMajorSequence", CIMMajorSequence, null, true);

    }

    @Override
    public Integer getCIMMajorSequenceMinLength() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequenceMinLength");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setCIMMajorSequenceMinLength(Integer CIMMajorSequenceMinLength) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMajorSequenceMinLength", CIMMajorSequenceMinLength, null, true);

    }

    @Override
    public String getCIMMajorSequencePadChar() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequencePadChar");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMajorSequencePadChar(String CIMMajorSequencePadChar) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMajorSequencePadChar", CIMMajorSequencePadChar, null, true);

    }

    @Override
    public String getCIMMajorSequenceType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequencePadChar");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMajorSequenceType(String CIMMajorSequenceType) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMajorSequenceType", CIMMajorSequenceType, null, true);

    }

    @Override
    public String getCIMMinorSequence() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequencePadChar");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMinorSequence(String CIMMinorSequence) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMinorSequence", CIMMinorSequence, null, true);

    }

    @Override
    public Integer getCIMMinorSequenceMinLength() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequenceMinLength");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setCIMMinorSequenceMinLength(Integer CIMMinorSequenceMinLength) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMinorSequenceMinLength", CIMMinorSequenceMinLength, null, true);

    }

    @Override
    public String getCIMMinorSequencePadChar() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequencePadChar");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMinorSequencePadChar(String CIMMinorSequencePadChar) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMinorSequencePadChar", CIMMinorSequencePadChar, null, true);

    }

    @Override
    public String getCIMMinorSequenceType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequencePadChar");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMinorSequenceType(String CIMMinorSequenceType) throws Exception {
        this.setPropertyValue("ICIMRevisionScheme", "CIMMinorSequenceType", CIMMinorSequenceType, null, true);

    }
}
