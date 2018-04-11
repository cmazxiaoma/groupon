package com.cmazxiaoma.framework.util;

import java.math.BigDecimal;

/**
 *
 */
public class ComputeUtil {

    /**
     * 生成指定精度的value
     *
     * @param value
     * @param scale
     * @param roudingMode
     * @return
     */
    public static double round(double value, int scale, int roudingMode) {
        BigDecimal bdBigDecimal = new BigDecimal(value);
        bdBigDecimal = bdBigDecimal.setScale(scale, roudingMode);
        double resultValue = bdBigDecimal.doubleValue();
        bdBigDecimal = null;
        return resultValue;
    }
}
