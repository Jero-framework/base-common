package com.jero.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * 随机数工具类
 *
 * @version 1.0
 * @author  zer0
 */
public class RandomUtils {

    /**
     * 从0~9中随机选择 bit 位生成字符串
     * @param bit 所生成字符串长度，默认6位
     * @return 数字随机组成的字符串
     */
    public static String randomNum(int bit){
        if (bit <= 0){
            bit = 6;
        }
        return RandomStringUtils.randomNumeric(bit);
    }

    /**
     * 从0~9、a~z、A~Z中随机选择 bit 位生成字符串
     * @param bit 所生成字符串长度，默认6位
     * @return 数字、字母随机组成的字符串
     */
    public static String random(int bit){
        if (bit <= 0){
            bit = 6;
        }
        return RandomStringUtils.random(bit, true, true);
    }

    /**
     * 从0~9、a~z中随机选择 bit 为生成字符串
     * @param bit 所生成字符串长度，默认6位
     * @return 数字、小写字母随机组成的字符串
     */
    public static String randomLower(int bit){
        return StringUtils.lowerCase(random(bit));
    }

    /**
     * 从0~9、A~Z中随机选择 bit 为生成字符串
     * @param bit 所生成字符串长度，默认6位
     * @return 数字、大写字母随机组成的字符串
     */
    public static String randomUpper(int bit){
        return StringUtils.upperCase(random(bit));
    }

}
