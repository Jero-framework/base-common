package com.jero.common.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


/** 
* StringUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 13, 2017</pre> 
* @version 1.0 
*/ 
public class StringUtilsTest { 

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }



    @Test
    public void testIsNumber() throws Exception {
        assertTrue(StringUtils.isNumber("383485"));
        assertTrue(StringUtils.isNumber("7463524153212"));
        assertFalse(StringUtils.isNumber("3ufh23234"));
        assertFalse(StringUtils.isNumber("384.3"));
        assertFalse(StringUtils.isNumber(".394"));
        assertFalse(StringUtils.isNumber("9438434h"));
    }

    @Test
    public void testIsFloat() throws Exception {
        assertTrue(StringUtils.isFloat("383458.24"));
        assertTrue(StringUtils.isFloat("34343."));
        assertFalse(StringUtils.isFloat(".3232"));
        assertFalse(StringUtils.isFloat("4242525"));
        assertFalse(StringUtils.isFloat("38444.344.443"));
        assertFalse(StringUtils.isFloat("3434..43"));
        assertFalse(StringUtils.isFloat("38434.ff"));
        assertFalse(StringUtils.isFloat("38h34.3"));
        assertFalse(StringUtils.isFloat("38h34.,3"));
        assertFalse(StringUtils.isFloat("."));
    }

    @Test
    public void testIsLetter() throws Exception {
        assertTrue(StringUtils.isLetter("AfjeuEhfje"));
        assertTrue(StringUtils.isLetter("ASDFEG"));
        assertTrue(StringUtils.isLetter("fjfnvvjv"));
        assertFalse(StringUtils.isLetter("fjhekf3hjfgdF"));
        assertFalse(StringUtils.isLetter("ASCFFGe,fe"));
        assertFalse(StringUtils.isLetter("发斯蒂芬"));
        assertFalse(StringUtils.isLetter(".jwiafjsa"));
        assertFalse(StringUtils.isLetter("jw iafjsa"));
        assertFalse(StringUtils.isLetter(null));
    }

    @Test
    public void testIsChinese() throws Exception {
        assertTrue(StringUtils.isChinese("发神经发送"));
        assertFalse(StringUtils.isChinese("法萨芬，防守打法"));
        assertFalse(StringUtils.isChinese("发多少d发到付"));
        assertFalse(StringUtils.isChinese(null));
        assertFalse(StringUtils.isChinese("2放到发"));
        assertFalse(StringUtils.isChinese(" 方式开发"));
        assertFalse(StringUtils.isChinese("放到 发生的"));
    }

} 
