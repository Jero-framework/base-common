package com.jero.common.utils;

import com.jero.commom.utils.ConvertUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


/**
* ConvertUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 16, 2017</pre> 
* @version 1.0 
*/ 
public class ConvertUtilsTest {

    Date date;

    @BeforeEach
    public void before() throws Exception {
        date = new Date(2017-1900, 8-1, 2, 11, 22, 33);
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testFormatStrFromDate() throws Exception {
        assertEquals("2017-08-02", ConvertUtils.formatStrFromDate(date));
    }

    @Test
    public void testFormatStrFromTime() throws Exception {
        assertEquals("2017-08-02 11:22:33", ConvertUtils.formatStrFromTime(date));
    }

    @Test
    public void testStrToDateStr() throws Exception {
        assertEquals(date, ConvertUtils.strToDate("2017-08-02 11:22:33"));
    }

    @Test
    public void testStrToDateForStrPattern() throws Exception {
        assertDoesNotThrow(() -> ConvertUtils.strToDate("2017/08/02 11:22:33", "yyyy/MM/dd HH:mm:ss"));
        assertDoesNotThrow(() -> ConvertUtils.strToDate("2017-08-02 11:22:33", null));
        assertThrows(ParseException.class, () -> ConvertUtils.strToDate("2017/08/02 11:22:33", null));
    }

}
