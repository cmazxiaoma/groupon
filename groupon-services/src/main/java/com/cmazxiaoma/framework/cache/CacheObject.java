package com.cmazxiaoma.framework.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/3/10 23:29
 */
@Data
public abstract class CacheObject implements Serializable {

    private Object id;
}
