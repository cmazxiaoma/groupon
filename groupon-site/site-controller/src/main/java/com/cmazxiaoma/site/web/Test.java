package com.cmazxiaoma.site.web;

import com.cmazxiaoma.framework.util.DateUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_MONTH;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/4/16 21:32
 */
public class Test {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println("今天=" + DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd"));
        calendar.add(Calendar.DATE, 1);
        System.out.println("明天=" + DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd"));
    }

    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }
}
