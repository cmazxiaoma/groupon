package com.cmazxiaoma.framework.cache;

import com.alibaba.fastjson.JSON;
import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Slf4j
public final class CacheUtil {

    private static Jedis jedis;

    private static Jedis getJedis() {
        if (null == jedis) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("cache");
                JedisPoolConfig config = new JedisPoolConfig();
                JedisPool pool = new JedisPool(config, bundle.getString("ip"),
                        Integer.parseInt(bundle.getString("port")), 1000);
                jedis = pool.getResource();
            } catch (Exception e) {
                //logger.error(e.getMessage(), e);
                return null;
            }
        }

        return jedis;
    }

    /**
     * 向缓存中增加实体
     *
     * @param key
     * @param entity
     */
    public static <T extends BaseEntity> void putEntity(String key, T entity) {
        if (null != getJedis()) {
            getJedis().set(key.getBytes(), JSON.toJSONBytes(entity));
        } else {
            log.info("缓存服务连接失败，不能放入数据(" + entity.toString() + ")");
        }
    }

    private static String generateKey(Class clazz, Object id) {
        return clazz.getName() + "." + id;
    }

    public static <T extends BaseEntity> void putEntity(T entity) {
        if (null != getJedis()) {
            String key = generateKey(entity.getClass(), entity.getId());
            getJedis().set(key.getBytes(), JSON.toJSONBytes(entity));
        } else {
            log.info("缓存服务连接失败，不能放入数据(" + entity.toString() + ")");
        }
    }

    public static <T extends BaseEntity> T getEntity(Long id, Class<T> clazz) {
        if (null != getJedis()) {
            String key = generateKey(clazz, id);
            byte[] bytes = getJedis().get(key.getBytes());
            if (null == bytes) {
                return null;
            }
            return JSON.parseObject(bytes, clazz);
        }

        return null;
    }

    public static <T extends CacheObject> void putCacheObject(T co) {
        if (null != getJedis()) {
            String key = generateKey(co.getClass(), co.getId());
            getJedis().set(key.getBytes(), JSON.toJSONBytes(co));
        }
    }

    public static <T extends BaseEntity> List<T> getAllEntities(Class<T> clazz) {
        if (null != getJedis()) {
            String keyPrefix = clazz.getName();
            Set<byte[]> keySet = getJedis().keys((keyPrefix + "*").getBytes());
            if (keySet.size() == 0) {
                return null;
            }
            T t;
            List<T> result = new ArrayList<>();
            List<byte[]> list = getJedis().mget(keySet.toArray(new byte[1][keySet.size()]));
            for (byte[] bytes : list) {
                t = JSON.parseObject(bytes, clazz);
                result.add(t);
            }
            return result;
        }
        return null;
    }

    public static <T extends BaseEntity> void deleteEntity(Class<T> clazz, Long id) {
        if (null != getJedis()) {
            getJedis().del(generateKey(clazz, id));
        }
    }

}