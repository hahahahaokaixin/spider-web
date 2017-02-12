package com.guo.base.dao;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;

import com.guo.base.model.TaskToBi;
import com.guo.base.util.Constant;

/**
 * 获取数据库
 */
public class SqlDataSource {
    private static Logger logger = Logger.getLogger(SqlDataSource.class);

    public final static Map<String, TaskToBi> Map = new Hashtable<String, TaskToBi>();

    public final static Object obj = new Object();

    public synchronized static TaskToBi getBiDataSource(String node) {
        return getBiData(node);

    }

    public static TaskToBi getBiData(String node) {
        TaskToBi toBi = null;
        synchronized (obj) {
            String key = node;

            toBi = Map.get(key);
            if (toBi == null) {
                try {
                    toBi = new TaskToBi();

                    BasicDataSource dataSource = new BasicDataSource();
                    dataSource.setDriverClassName(Constant.DbDriver);
                    dataSource.setUrl(Constant.DbUrl);
                    dataSource.setUsername(Constant.DbUsername);
                    dataSource.setPassword(Constant.DbPwd);
                    dataSource.setTestOnBorrow(true);
                    dataSource.setTestOnReturn(true);
                    dataSource.setTestWhileIdle(true);
                    dataSource.setValidationQuery("SELECT 1 ");
                    Dao biDao = new NutDao(dataSource);

                    toBi.setBiDao(biDao);
                    Map.put(key, toBi);

                } catch (Exception e) {
                    logger.error("*************node************" + node);
                    logger.error("", e);
                }

            }

        }
        return toBi;
    }

}
