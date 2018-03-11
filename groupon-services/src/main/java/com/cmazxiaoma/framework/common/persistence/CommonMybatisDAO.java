package com.cmazxiaoma.framework.common.persistence;

import com.cmazxiaoma.framework.base.context.SpringApplicationContext;
import com.cmazxiaoma.framework.base.entity.BaseEntity;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * 通用DAO
 */
public final class CommonMybatisDAO {

    private static SqlSessionTemplate template = SpringApplicationContext.getBean(SqlSessionTemplate.class);

    //使用场景
    //业务service的方法调用CommonMybatisDAO的save方法保存实体进入数据库
    private static <T extends BaseEntity> String getSqlId(Class<T> clazz) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        return clazz.getName() + "." + element.getMethodName();
    }

    private static <T extends BaseEntity> String getMapperName(Class<T> clazz) {
        return clazz.getName() + "Mapper.";
    }

    public static <T extends BaseEntity> void save(T entity) {
        String sqlId = getSqlId(entity.getClass());
        template.insert(sqlId, entity);
    }

    public static <T extends BaseEntity> void save(String sqlId, T entity) {
        String finalSqlId = getMapperName(entity.getClass()) + sqlId;
        template.insert(finalSqlId, entity);
    }

    public static <T extends BaseEntity> List<T> findAll(Class<T> clazz) {
        String sqlId = getSqlId(clazz);
        return template.selectList(sqlId);
    }

    public static <T extends BaseEntity> List<T> findAll(Class<T> clazz, String sqlId) {
        return template.selectList(getMapperName(clazz) + sqlId);
    }
}
