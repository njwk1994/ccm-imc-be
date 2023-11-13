package com.imc.general.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.constant.RelDefConstant;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.IObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/9/22 14:37
 */
@Slf4j
@Service
public class CreateTreeConfigAndItems extends ServerApiBase<String> {

    public static final String TREE_CONF = "treeConf";
    public static final String TREE_CONF_ITEMS = "treeConfItems";

    private String treeUid;

    @Override
    public void onHandle() {
        JSONObject treeConf = Optional.ofNullable(this.requestParam.getJSONObject(TREE_CONF)).orElseThrow(() -> new RuntimeException("树配置参数不可为空!"));
        JSONArray treeConfItems = Optional.ofNullable(this.requestParam.getJSONArray(TREE_CONF_ITEMS)).orElseGet(JSONArray::new);
        String username = GeneralUtil.getUsername();
        ObjectCollection transaction = new ObjectCollection(username);
        IObject treeConfig = SchemaUtil.newIObject(treeConf, transaction, username);
        for (int i = 0; i < treeConfItems.size(); i++) {
            JSONObject item = treeConfItems.getJSONObject(i);
            IObject currentTreeConfigItem = SchemaUtil.newIObject(item, transaction, username);
            try {
                SchemaUtil.newRelationship(transaction, RelDefConstant.TREE_TREE_ITEM, treeConfig, currentTreeConfigItem);
            } catch (Exception e) {
                log.error("创建目录树配置关联关系失败!{}", ExceptionUtil.getRootErrorMessage(e));
                throw new RuntimeException(String.format("创建目录树配置关联关系失败!%s", ExceptionUtil.getRootErrorMessage(e)));
            }
        }
        try {
            transaction.commit();
        } catch (Exception e) {
            log.error("一次性生成树配置和配置项失败,提交事务异常!{}", ExceptionUtil.getRootErrorMessage(e));
            throw new RuntimeException(String.format("一次性生成树配置和配置项失败,提交事务异常!%s", ExceptionUtil.getRootErrorMessage(e)));
        }
        this.treeUid = treeConfig.getUid();
    }

    @Override
    public String onSerialize() {
        return this.treeUid;
    }

    @Override
    public IServerApi<String> nullInstance() {
        return new CreateTreeConfigAndItems();
    }
}
