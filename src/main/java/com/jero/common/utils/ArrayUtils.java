package com.jero.common.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组操作工具类
 *
 * @author zer0
 * @version 1.0
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils{

    private ArrayUtils() {
        throw new IllegalStateException("ArrayUtils Utility class");
    }

    /**
     * 列表转字符串，"逗号"分隔
     * @param stringList 转string的列表
     * @return 逗号分隔的字符串
     */
    public static String listToString(List<String> stringList) {
        if (CollectionUtils.isEmpty(stringList)) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stringList.size(); i++) {
            builder.append(stringList.get(i));
            if (i < stringList.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    /**
     * 字符串转列表，逗号分割
     * @param string 待分割字符串
     * @return 分割完成的字符串，组装成列表
     */
    public static List<String> stringToList(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }

        return stringToList(string, ",");
    }

    /**
     * 字符串转列表
     * @param string 待分割字符串
     * @param split 分割符
     * @return 分割完成的字符串，组装成列表
     */
    public static List<String> stringToList(String string, String split) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }

        return Arrays.asList(string.split(split));
    }

    /**
     * 数字字符串转列表，默认逗号分割
     * @param string 待分割数字字符串
     * @return 分割完成的整数，组装成列表
     */
    public static List<Integer> stringToIntList(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }

        return stringToIntList(string, ",");
    }

    /**
     * 数字字符串转列表
     * @param string 待分割数字字符串
     * @param split 分割符
     * @return 分割完成的整数，组装成列表
     */
    public static List<Integer> stringToIntList(String string, String split) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }

        List<Integer> integerList = new ArrayList<Integer>();
        String[] stringArray = string.split(split);
        for (int i = 0; i < stringArray.length; i++) {
            String str = stringArray[i];
            if (StringUtils.isNotEmpty(str)) {
                integerList.add(Integer.valueOf(str));
            }
        }
        return integerList;
    }

}
