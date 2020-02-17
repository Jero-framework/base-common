package com.jero.common.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomUtilsTest {
    
    @Test
    public void testRandomNum() throws Exception {
        String result = RandomUtils.randomNum(0);
        assertEquals(6, result.length());

        String result2 = RandomUtils.randomNum(3);
        assertEquals(3, result2.length());
    }

    @Test
    public void testRandom() throws Exception {
        String result = RandomUtils.random(0);
        assertEquals(6, result.length());

        String result2 = RandomUtils.random(8);
        assertEquals(8, result2.length());
    }

    @Test
    public void testRandomLower() throws Exception {
        Pattern pattern = Pattern.compile("^[0-9a-z]+$");

        String result = RandomUtils.randomLower(0);
        Matcher m1 = pattern.matcher(result);
        assertEquals(6, result.length());
        assertTrue(m1.matches());

        String result2 = RandomUtils.randomLower(9);
        Matcher m2 = pattern.matcher(result2);
        assertEquals(9, result2.length());
        assertTrue(m2.matches());
    }

    @Test
    public void testRandomUpper() throws Exception {
        Pattern pattern = Pattern.compile("^[0-9A-Z]+$");

        String result = RandomUtils.randomUpper(0);
        Matcher m1 = pattern.matcher(result);
        assertEquals(6, result.length());
        assertTrue(m1.matches());

        String result2 = RandomUtils.randomUpper(9);
        Matcher m2 = pattern.matcher(result2);
        assertEquals(9, result2.length());
        assertTrue(m2.matches());

        String result3 = RandomUtils.randomUpper(-1);
        Matcher m3 = pattern.matcher(result3);
        assertEquals(6, result3.length());
        assertTrue(m3.matches());
    }
    
}
