//package com.ccm.packagemanage.datasource;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.ccm.modules.packagemanage.enums.DataSourceType;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.sql.JDBCType;
//import java.sql.SQLType;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class JDBCDataSourceFactoryTest {
//
//  @Autowired
//  IDataSourceDiscoveryService iDataSourceDiscoveryService;
//
//  @Test
//  public void TestJDBCDataSourceFactory() {
//    // 连接配置
//    JSONObject jsonObject = new JSONObject();
//    jsonObject.put("url", "jdbc:mysql://localhost:3306/externaldb?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
//    jsonObject.put("username", "root");
//    jsonObject.put("password", "password");
//    DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();
//    dataSourceConfiguration.setData(jsonObject);
//
//    // 初始化连接
//    if (!JDBCDataSourceFactory.initJDBCDataSource(dataSourceConfiguration, DataSourceType.MYSQL, "1")) {
//      System.out.println("连接失败, 请检查连接参数");
//      return;
//    }
//
//    // 获得连接
//    IJDBCDataSource ijdbcDataSource = JDBCDataSourceFactory.getJdbcDataSourceById("1");
//    // 执行事务
//    LinkedList<ProcedureParam> params = new LinkedList<>();
//    params.add(new ProcedureParam(JDBCType.INTEGER, 2));
//    params.add(new ProcedureParam(JDBCType.VARCHAR, "ONE"));
//    params.add(new ProcedureParam(JDBCType.VARCHAR, "ONECODE"));
//    LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
//    outTypes.put("message", JDBCType.VARCHAR);
//    HashMap<String, Object> result = new HashMap<>();
//    if (!ijdbcDataSource.executeCall("{ call insert_data(?, ?, ?, ?) }", params, outTypes, result)) {
//      System.out.println("执行存储过程失败");
//      return;
//    }
//
//    // 打印结果
//    System.out.println(JSON.toJSONString(result));
//  }
//}