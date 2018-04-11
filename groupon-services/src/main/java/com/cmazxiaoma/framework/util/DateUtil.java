package com.cmazxiaoma.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 */
public final class DateUtil {

    private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Map<String, ThreadLocal<SimpleDateFormat>> dateFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Object timestampFormatLock = new Object();

    private static final Object dateFormatLock = new Object();

    private static String dateFormatPattern = "yyyy-MM-dd";

    private static String dateFormatPattern_CN = "yyyy年MM月dd日";

    private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";

    private static String simplifyDatePattern = "yyyyMMdd";

    private static String simplifyTimestampPattern = "yyyyMMddHHmmss";

    private static String timestampPatternCps = "yyyyMMddHHmmss";

    private static SimpleDateFormat getDateFormatCN() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern_CN);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(dateFormatPattern_CN);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormatPattern_CN);
                        }
                    };
                    dateFormatPool.put(dateFormatPattern_CN, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getDateFormat() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(dateFormatPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormatPattern);
                        }
                    };
                    dateFormatPool.put(dateFormatPattern, tl);
                }
            }
        }
        return tl.get();
    }

    public static SimpleDateFormat getTimestampFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(timestampPattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(timestampPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampPattern);
                        }
                    };
                    timestampFormatPool.put(timestampPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getSimplifyTimestampFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(simplifyTimestampPattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(simplifyTimestampPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(simplifyTimestampPattern);
                        }
                    };
                    timestampFormatPool.put(simplifyTimestampPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getSimplifyDateFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(simplifyDatePattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(simplifyDatePattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(simplifyDatePattern);
                        }
                    };
                    timestampFormatPool.put(simplifyDatePattern, tl);
                }
            }
        }
        return tl.get();
    }

    public static SimpleDateFormat getTimestampFormatCps() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(timestampPatternCps);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(timestampPatternCps);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampPatternCps);
                        }
                    };
                    timestampFormatPool.put(timestampPatternCps, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return    "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getTimestampFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return    "年年年年月月日日时时分分秒秒"格式的日期字符串
     */
    public static String simplifyTimestampFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getSimplifyTimestampFormat().format(date);
    }

    /**
     * 格式化成日期格式
     *
     * @param date 要格式化的日期对象
     * @return    "年年年年月月日日"格式的日期字符串
     */
    public static String simplifyDateFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getSimplifyDateFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return    "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormatCps(Date date) {
        if (date == null) {
            return "";
        }
        return getTimestampFormatCps().format(date);
    }

    /**
     * 格式化成Unix时间戳格式
     *
     * @param date
     * @return
     */
    public static long unixTimestampFormat(Date date) {
        String unixDate = String.valueOf(date.getTime()).substring(0, 10);
        return Long.parseLong(unixDate);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param datetime 要格式化的日期
     * @return    "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormat(long datetime) {
        return getTimestampFormat().format(new Date(datetime));
    }

    /**
     * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成Long型日期
     *
     * @param timestampStr 年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     * @return Long型日期
     */
    public static long formatTimestampToLong(String timestampStr) {
        try {
            return getTimestampFormat().parse(timestampStr).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    /**
     * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成日期
     *
     * @param timestampStr 年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     * @return 日期
     */
    public static Date formatTimestampToDate(String timestampStr) {
        try {
            return getTimestampFormat().parse(timestampStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将"年年年年-月月-日日"格式的日期字符串转换成日期
     *
     * @param dateStr 年年年年-月月-日日"格式的日期字符串
     * @return 日期
     */
    public static Date formatDateToDate(String dateStr) {
        try {
            return getDateFormat().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化成日期格式
     *
     * @param date 要格式化的日期
     * @return    "年年年年-月月-日日"格式的日期字符串
     */
    public static String dateFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getDateFormat().format(date);
    }

    /**
     * 格式化成日期格式
     *
     * @param date 要格式化的日期
     * @return    "××××年××月××日"格式的日期字符串
     */
    public static String dateFormatCN(Date date) {
        if (date == null) {
            return "";
        }
        return getDateFormatCN().format(date);
    }

    /**
     * 格式化成日期格式
     *
     * @param datetime 要格式化的日期
     * @return    "年年年年-月月-日日"格式的日期字符串
     */
    public static String dateFormat(long datetime) {
        return getDateFormat().format(new Date(datetime));
    }

    /**
     * 将"年年年年-月月-日日"格式的日期字符串转换成Long型日期
     *
     * @param dateStr "年年年年-月月-日日"格式的日期字符串
     * @return Long型日期
     */
    public static long formatDateToLong(String dateStr) {
        try {
            return getDateFormat().parse(dateStr).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    /**
     * 得到本月的第一天
     *
     * @return 以"年年年年-月月-日日"格式返回当前月第一天的日期
     */
    public static String getFirstDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDateFormat().format(calendar.getTime());
    }

    /**
     * 得到本月的最后一天
     *
     * @return 以"年年年年-月月-日日"格式返回当前月最后一天的日期
     */
    public static String getLastDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateFormat().format(calendar.getTime());
    }

    /**
     * 获取当前日期前（后）的某一天
     *
     * @param offset 偏移量，即当前日期之前（后）多少天，如果是之前，offset为负的整数
     * @return 以"年年年年-月月-日日"格式返回要获取的日期
     */
    public static Date getDayAfterCurrentDate(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }

    /**
     * 返回以当前时间为基准的七日的结束销售时间
     *
     * @return
     */
    public static Date getSevenDaysAfterOnSale() {
        Calendar calendarAdd = Calendar.getInstance();
        calendarAdd.setTime(new Date());

        calendarAdd.add(Calendar.DAY_OF_MONTH, 6);
        calendarAdd.set(Calendar.HOUR_OF_DAY, 23);
        calendarAdd.set(Calendar.MINUTE, 59);
        calendarAdd.set(Calendar.SECOND, 59);
        return calendarAdd.getTime();
    }

    /**
     * 根据指定的时间参数获取时间
     *
     * @return
     */
    public static Date getTimeByIdentifiedValues(Integer year, Integer month, Integer day, Integer hour,
                                                 Integer minute, Integer second) {
        Calendar calendarAdd = Calendar.getInstance();
        calendarAdd.setTime(new Date());

        calendarAdd.add(Calendar.DAY_OF_MONTH, month);
        calendarAdd.set(Calendar.HOUR_OF_DAY, day);
        calendarAdd.set(Calendar.MINUTE, minute);
        calendarAdd.set(Calendar.SECOND, second);
        return calendarAdd.getTime();
    }

    /**
     * 获取默认日期时间
     *
     * @return
     */
    public static Date getDefaultDateTime() {
        return new Date(formatTimestampToDate("1970-01-01 00:00:00").getTime());
    }
    //未完，待续
}