package com.cmazxiaoma.framework.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/10 17:23
 */
@Data
public abstract class BaseEntity implements Serializable {

    private Long id;

    private String routerCall;

    /**
     * 获取给定实体集合的ID集合
     * @param entities 实体集合
     * @param <T> 实体类型
     * @return ID集合
     */
    public static <T extends BaseEntity> List<Long> idList(List<T> entities) {
        return entities.stream().map(entity -> entity.getId())
                .collect(Collectors.toList());
    }

    /**
     * 将给定的实体集合转换成实体ID位key, value的Map
     * @param entities
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> Map<Long, T> idEntityMap(List<T> entities) {
        Map<Long, T> idMap = entities.stream().collect(Collectors.toMap(T::getId, entity -> entity));
        return idMap;
    }

}
