package com.jero.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @version 1.0
 * @author  zer0
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    private StringUtils() {
        throw new IllegalStateException("StringUtils Utility class");
    }

    /**
     * 判断字符串是否为数字表示
     * @param str 判断的字符串
     * @return 为数字返回true，不为数字返回false
     */
    public static boolean isNumber(String str){
        boolean res = false;
        if (str != null && str.length() > 0){
            Pattern pattern = Pattern.compile("^\\d+$");
            Matcher m = pattern.matcher(str);
            if (m.find()){
                res = true;
            }
        }
        return res;
    }

    /**
     * 判断字符串是否为浮点型表示
     * @param str 判断的字符串
     * @return 为浮点型返回true，不为浮点型返回false
     */
    public static boolean isFloat(String str){
        boolean res = false;
        if (str != null && str.length() > 0){
            Pattern pattern = Pattern.compile("^\\d+\\.\\d*$");
            Matcher m = pattern.matcher(str);
            if (m.find()){
                res = true;
            }
        }
        return res;
    }

    /**
     * 判断字符串是否为纯字母
     * @param str 判断的字符串
     * @return 为纯字母返回true，不为纯字母返回false
     */
    public static boolean isLetter(String str){
        boolean res = false;
        if (str != null && str.length() > 0){
            Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
            Matcher m = pattern.matcher(str);
            if (m.find()){
                res = true;
            }
        }
        return  res;
    }

    /**
     * 判断字符串是否为纯中文
     * @param str 判断的字符串
     * @return 为纯中文返回true，不为纯中文返回false
     */
    public static boolean isChinese(String str){
        boolean res = false;
        if (str != null && str.length() > 0){
            Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
            Matcher m = pattern.matcher(str);
            if (m.find()){
                res = true;
            }
        }
        return res;
    }

    /**
     * 判断字符串是否为邮箱
     * @param str 判断的字符串
     * @return boolean
     */
    public static boolean isEmail(String str){
        boolean res = false;
        if (str != null && str.length() > 0){
            Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$");
            Matcher m = pattern.matcher(str);
            if (m.find()){
                res = true;
            }
        }
        return res;
    }

    /**
     * 判断字符串是否为电话号码
     * @param str 判断的字符串
     * @return boolean
     */
    public static boolean isPhone(String str){
        boolean res = false;
        if (str != null && str.length() > 0){
            Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
            Matcher m = pattern.matcher(str);
            if (m.find()){
                res = true;
            }
        }
        return res;
    }


}
