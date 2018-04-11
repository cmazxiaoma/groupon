package com.cmazxiaoma.framework.util;


import com.cmazxiaoma.framework.base.exception.FrameworkException;

/**
 *
 */
public final class StringUtil {

    /**
     * 字符串非空判断
     *
     * @param target 目标字符串
     * @return
     */
    public static final boolean isEmpty(String target) {
        return target == null || target.length() == 0 || target.trim().length() == 0;
    }

    /**
     * 字符串非空判断
     *
     * @param target 目标字符串
     * @return
     */
    public static final boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }

    /**
     * @param target
     * @return
     */
    public static final String convertFirstChar2LowerCase(String target) {
        if (isEmpty(target)) {
            throw new FrameworkException("The input string is empty!");
        }
        String firstChar = target.substring(0, 1);
        return firstChar.toLowerCase() + target.substring(1, target.length());
    }

    /**
     * 根据字母顺序排序单词
     *
     * @param words
     * @return
     */
    public static String[] sortWords(String[] words) {
        String t;
        for (int i = 0; i < words.length; i++) {
            for (int j = i; j < words.length; j++) {
                if (words[i].toLowerCase().compareTo(words[j].toLowerCase()) > 0) {
                    t = words[i];
                    words[i] = words[j];
                    words[j] = t;
                }
            }
        }
        return words;
    }
}
