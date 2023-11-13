package com.imc.schema.interfaces.defaults;

import com.alibaba.excel.util.StringUtils;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.bases.ICIMCCMPriorityItemBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ICIMCCMPriorityItemDefault extends ICIMCCMPriorityItemBase {
    public ICIMCCMPriorityItemDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public boolean isHint(IObject object) throws Exception {
        boolean result = false;
        if (object != null) {
            String propertyDefinitionUID = this.getPriorityTargetProperty();
            String operator = this.getOperator();
//            String expectedValue = CIMContext.Instance.ProcessCache().parseExpectedValue(propertyDefinitionUID, this.getPriorityExpectedValue());
            String expectedValue = this.getPriorityExpectedValue();
            Object lstrProvidedValue = ((ICIMCCMDesignObjDefault) object).getValue(propertyDefinitionUID);
            String providedValue = lstrProvidedValue != null ? lstrProvidedValue.toString() : null;
            result = this.doHint(providedValue, operator, expectedValue);
        }
        return result;
    }

    protected boolean doHint(String value, String operator, String expectedValue) {
        if (operator == null || StringUtils.isEmpty(operator))
            operator = "ELT_=_Operator";
        if (value == null)
            value = "";
        if (expectedValue == null)
            expectedValue = "";
        log.trace("enter to do-hint under priority item with identification status:" + value + " " + operator + " " + expectedValue);


        if (operator.equalsIgnoreCase("ELT_=_Operator")) {
            return value.equalsIgnoreCase(expectedValue);
        } else if (operator.equalsIgnoreCase("ELT_!=_Operator")) {
            return !value.equalsIgnoreCase(expectedValue);
        } else {
            double dblValue = Double.parseDouble(value);
            double dblExpectedValue = Double.parseDouble(expectedValue);

            if (operator.equalsIgnoreCase("ELT_<_Operator")) {
                return dblValue < dblExpectedValue;
            } else if (operator.equalsIgnoreCase("ELT_<=_Operator")) {
                return dblValue <= dblExpectedValue;
            } else if (operator.equalsIgnoreCase("ELT_>_Operator")) {
                return dblValue > dblExpectedValue;
            } else if (operator.equalsIgnoreCase("ELT_>=_Operator")) {
                return dblValue >= dblExpectedValue;
            }
        }
        return false;
    }
}
