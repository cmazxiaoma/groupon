package com.cmazxiaoma.admin.base.router;

import com.cmazxiaoma.admin.base.controller.AjaxResult;
import com.cmazxiaoma.framework.base.entity.BaseEntity;
import com.cmazxiaoma.framework.base.exception.BusinessException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public abstract class BaseRouter {

    protected Class clazz;

    private Map<String, String> methodDisplayNameMap = new HashMap<>();

    public boolean isAuthorizedToCall(Long userId, String method) {
        return true;
    }

    public boolean isButtonDisabled(BaseEntity entity, String method) {
        return false;
    }

    public String getMethodDisplayName(String method) {
        return methodDisplayNameMap.get(method);
    }

    protected void addMethodDisplayName(String method, String methodDisplayName) {
        methodDisplayNameMap.put(method, methodDisplayName);
    }

    public abstract <T extends BaseEntity> T loadEntity(Long entityId);

    /**
     * 调用具体Router的业务方法(通过反射)
     *
     * @param user   用户
     * @param entity 实体
     * @param method 方法(字符串)
     * @param params Map参数
     * @param <T>    实体泛型
     * @return
     */
    public <T extends BaseEntity> AjaxResult callMethod(BaseEntity user, T entity, String method, Map<String, Object> params) {
        //根据方法名获得到具体Router的方法
        Method defineMethod;

        try {
            //反射得到方法对象
            defineMethod = getClass().getMethod(method, new Class[]{BaseEntity.class, clazz, Map.class});
            if (null != defineMethod) {
                //方法调用
                return (AjaxResult) defineMethod.invoke(this, user, entity, params);
            }
            throw new RuntimeException("No method " + method + " defined for router " + getClass().getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            if (e.getTargetException() instanceof BusinessException) {
                throw (BusinessException) e.getTargetException();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

}
