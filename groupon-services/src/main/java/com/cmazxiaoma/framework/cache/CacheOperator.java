package com.cmazxiaoma.framework.cache;

import com.alibaba.fastjson.JSON;
import com.cmazxiaoma.framework.base.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/10 23:31
 */
@Slf4j
public abstract class CacheOperator {

    private Jedis jedis;

    private Jedis getJedis() {
        if (jedis == null) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("cache");
                JedisPoolConfig config = new JedisPoolConfig();
                JedisPool pool = new JedisPool(config, bundle.getString("ip"),
                       Integer.parseInt(bundle.getString("port")), 1000);
                jedis = pool.getResource();
            } catch (Exception e) {
                log.error("获取jedis出错,原因 = {}", e.getMessage());
            }
        }

        return jedis;
    }

    /**
     * 向缓存中添加实体
     * @param key
     * @param entity
     * @param <T>
     */
    protected <T extends BaseEntity> void putEntity(String key, T entity) {
        if (this.getJedis() != null) {
            this.getJedis().set(key.getBytes(), JSON.toJSONBytes(entity));
        } else {
            log.info("redis连接失败,不能放入数据" + entity.toString());
        }
    }

    /**
     * 向缓存中添加实体集合
     * @param key
     * @param entityList
     * @param <T>
     */
    protected <T extends BaseEntity> void putEntities(String key, List<BaseEntity> entityList) {
        if (this.getJedis() != null) {
            this.getJedis().set(key.getBytes(), JSON.toJSONBytes(entityList));
        }
    }

    /**
     * 向缓存中添加缓存对象
     * @param key
     * @param cacheObject
     * @param <T>
     */
    protected <T extends CacheObject> void putCacheObject(String key, CacheObject cacheObject) {
        if (this.getJedis() != null) {
           this.getJedis().set(key.getBytes(), JSON.toJSONBytes(cacheObject));
        }
    }

    /**
     * 从缓存中获取实体
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T extends BaseEntity> T getEntity(String key, Class<T> clazz) {
        if (this.getJedis() != null) {
            byte[] bytes = this.getJedis().get(key.getBytes());

            if (bytes == null) {
                return null;
            }
            return JSON.parseObject(bytes, clazz);
        }
        return null;
    }

    /**
     * 从缓存中获取缓存对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T extends CacheObject> T getCacheObject(String key, Class<T> clazz) {
        if (this.getJedis() != null) {
            byte[] bytes = this.getJedis().get(key.getBytes());

            if (bytes == null) {
                return null;
            }
            return JSON.parseObject(bytes, clazz);
        }
        return null;
    }

    /**
     * 从缓存中获得实体集合
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T extends BaseEntity> List<T> getEntities(String key, Class<T> clazz) {
        try {
            if (this.getJedis() != null) {
                byte[] bytes = this.getJedis().get(key.getBytes());

                if (bytes == null) {
                    return null;
                }
                return JSON.parseArray(new String(bytes, "UTF-8"),
                        clazz);
            }
        } catch (Exception e) {
            log.error("从缓存中获得" + clazz.getName() + "集合实体失败, 原因 = {}", e.getMessage());
        }

        return null;
    }

    /**
     * 从缓存中删除实体/实体集合/缓存对象
     */
    protected void delete(String key) {
        if (this.getJedis() != null) {
            this.getJedis().del(key.getBytes());
        }
    }

    /**
     * 根据key前缀获取对应的所有实体
     * @param keyPrefix
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T extends BaseEntity> List<T> getEntitiesByKeyPrefix(String keyPrefix,
                                                                    Class<T> clazz) {
        if (this.getJedis() != null) {
            Set<byte[]> keySet = this.getJedis().keys((keyPrefix + "*").getBytes());

            if (keySet.size() == 0) {
                return null;
            }
            T t;
            List<T> result = new ArrayList<>();
            List<byte[]> list = this.getJedis().mget(keySet.toArray(new byte[1][keySet.size()]));

            for (byte[] bytes : list) {
                t = JSON.parseObject(bytes, clazz);
                result.add(t);
            }
            return result;
        }
        return null;
    }

    /**
     * 根据key前缀获取对应的所有缓存对象
     * @param keyPrefix
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T extends CacheObject> List<T> getCacheObjectsByKeyPrefix(String keyPrefix, Class<T> clazz) {
        if (this.getJedis() != null) {
            Set<byte[]> keySet = this.getJedis().keys((keyPrefix + "*").getBytes());
            List<byte[]> list = this.getJedis().mget(new byte[1][keySet.size()]);

            List<T> result = new ArrayList<>();
            T t;

            for (byte[] bytes : list) {
                t = JSON.parseObject(bytes, clazz);
                result.add(t);
            }
            return result;
        }
        return null;
    }
}
