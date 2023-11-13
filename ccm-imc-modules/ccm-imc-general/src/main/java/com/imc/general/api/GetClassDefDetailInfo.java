package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.model.frame.MJSONObject;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.modules.general.vo.ClassDefDetailVO;
import com.imc.modules.general.vo.formDesign.InterfaceDetailVO;
import com.imc.modules.general.vo.formDesign.PropertyDetailVO;
import com.imc.modules.general.vo.formDesign.RelDetailVO;
import com.imc.schema.interfaces.IRelDef;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetClassDefDetailInfo extends ServerApiBase<ClassDefDetailVO> {

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    private String classDef;

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (this.requestParam != null) {
            this.classDef = this.requestParam.getString(PropertyDefinitions.classDefinitionUid.name());
        }
    }

    private final ClassDefDetailVO classDefDetailVO = new ClassDefDetailVO();

    @SneakyThrows
    @Override
    public void onHandle() {
        if (!StringUtils.hasText(this.classDef)) {
            throw new RuntimeException(HandlerExceptionUtils.paramsInvalid(PropertyDefinitions.classDefinitionUid.name()));
        }
        //1.获取关联信息
        Map<RelDirection, List<IRelDef>> linkedRelDefsForClassDef = Context.Instance.getCacheHelper().getLinkedRelDefsForClassDef(this.classDef);
        if (CommonUtils.hasValue(linkedRelDefsForClassDef)) {
            for (Map.Entry<RelDirection, List<IRelDef>> entry : linkedRelDefsForClassDef.entrySet()) {
                RelDirection relDirection = entry.getKey();
                List<IRelDef> relDefs = entry.getValue();
                classDefDetailVO.getRelInfo().addAll(relDefs.stream().map(r -> new RelDetailVO(r, relDirection)).collect(Collectors.toList()));
            }
        }
        List<String> interfaceDefUids = new ArrayList<>();
        //2.获取接口信息
        Map<Boolean, List<MJSONObject>> interfaceDefsForClassDef = Context.Instance.getCacheHelper().getInterfaceDefsForClassDef(this.classDef);
        if (CommonUtils.hasValue(interfaceDefsForClassDef)) {
            for (Map.Entry<Boolean, List<MJSONObject>> entry : interfaceDefsForClassDef.entrySet()) {
                classDefDetailVO.getInterfaceInfo().addAll(entry.getValue().stream().map(r -> new InterfaceDetailVO(r, entry.getKey())).collect(Collectors.toList()));
                interfaceDefUids.addAll(entry.getValue().stream().map(MJSONObject::getUid).collect(Collectors.toList()));
            }
        }
        interfaceDefUids = interfaceDefUids.stream().distinct().collect(Collectors.toList());
        //3.获取属性信息
        Map<String, List<MJSONObject>> propertyDefsByInterfaceDefs = Context.Instance.getCacheHelper().getPropertyDefsByInterfaceDefs(interfaceDefUids);
        if (CommonUtils.hasValue(propertyDefsByInterfaceDefs)) {
            for (Map.Entry<String, List<MJSONObject>> entry : propertyDefsByInterfaceDefs.entrySet()) {
                classDefDetailVO.getPropertyInfo().addAll(entry.getValue().stream().map(PropertyDetailVO::new).collect(Collectors.toList()));
            }
        }
    }

    @Override
    public ClassDefDetailVO onSerialize() {
        return classDefDetailVO;
    }

    @Override
    public IServerApi<ClassDefDetailVO> nullInstance() {
        return new GetClassDefDetailInfo();
    }
}
