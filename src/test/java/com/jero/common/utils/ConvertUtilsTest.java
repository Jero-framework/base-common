package com.jero.common.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
    LocalDateTime localDateTime;
    LocalDate localDate;

    @BeforeEach
    public void before() throws Exception {
        date = new Date(2017-1900, 8-1, 2, 11, 22, 33);
        localDateTime = LocalDateTime.of(2020, 6, 10, 22, 13, 55);
        localDate = LocalDate.of(2020,6,9);
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testFormatStrFromDate() throws Exception {
        assertEquals("2017-08-02", ConvertUtils.formatStrFromDate(date));
    }

    @Test
    public void testFormatStrFromLocalDate() throws Exception {
        assertEquals("2020-06-09", ConvertUtils.formatStrFromLocalDate(localDate));
    }

    @Test
    public void testFormatStrFromTime() throws Exception {
        assertEquals("2017-08-02 11:22:33", ConvertUtils.formatStrFromTime(date));
    }

    @Test
    public void testFormatStrFromLocalDateTime() throws Exception {
        assertEquals("2020-06-10 22:13:55", ConvertUtils.formatStrFromLocalDateTime(localDateTime));
    }

    @Test
    public void testFormatStrFromDateByPattern() throws Exception{
        assertEquals("2017-08-02 11:22:33", ConvertUtils.formatStrFromDateByPattern(date, ConvertUtils.DATETIME_PATTERN));
        assertEquals("2017-08-02", ConvertUtils.formatStrFromDateByPattern(date, ConvertUtils.DAY_PATTERN));
        assertEquals("2017-08-02 11:22:33.000", ConvertUtils.formatStrFromDateByPattern(date, ConvertUtils.DATETIME_MS_PATTERN));
    }

    @Test
    public void testStrToDateStr() throws Exception {
        assertEquals(date, ConvertUtils.strToDate("2017-08-02 11:22:33"));
    }

    @Test
    public void testStrToLocalDateTimeStr() throws Exception {
        assertEquals(localDateTime, ConvertUtils.strToLocalDateTime("2020-06-10 22:13:55"));
    }

    @Test
    public void testStrToLocalDateStr() throws Exception {
        assertEquals(localDate, ConvertUtils.strToLocalDate("2020-06-09"));
    }

    @Test
    public void testStrToDateForStrPattern() throws Exception {
        assertDoesNotThrow(() -> ConvertUtils.strToDate("2017/08/02 11:22:33", "yyyy/MM/dd HH:mm:ss"));
        assertDoesNotThrow(() -> ConvertUtils.strToDate("2017-08-02 11:22:33", null));
        assertThrows(ParseException.class, () -> ConvertUtils.strToDate("2017/08/02 11:22:33", null));
    }

    @Test
    public void testStrToLocalDateTimePattern() throws Exception {
        assertDoesNotThrow(() -> ConvertUtils.strToLocalDateTime("2017/08/02 11:22:33", "yyyy/MM/dd HH:mm:ss"));
        assertDoesNotThrow(() -> ConvertUtils.strToLocalDateTime("2017-08-02 11:22:33", null));
        assertThrows(DateTimeParseException.class, () -> ConvertUtils.strToLocalDateTime("2017/08/02 11:22:33", null));
        assertEquals(localDateTime, ConvertUtils.strToLocalDateTime("2020-06-10 22:13:55", ConvertUtils.DATETIME_PATTERN));
        assertEquals(localDateTime, ConvertUtils.strToLocalDateTime("2020-06-10 22:13:55", null));
    }

}
