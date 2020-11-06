package com.jero.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 文件工具类测试包
 *
 * @author lixuetao
 * @version 1.0
 */
public class FileUtilsTest {

    private String path = "http://www.lxt.com/path/excel.xls";
    private String fileName = "test.pdf";

    @Test
    public void testGetExtendFileNameBlank() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getExtend(""));
    }

    @Test
    public void testGetExtendFileNameNull() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getExtend(null));
    }

    @Test
    public void testGetExtend() {
        assertEquals("pdf", FileUtils.getExtend(fileName));
        assertEquals("xls", FileUtils.getExtend(path));
    }

    @Test
    public void testGetFileNameBlank() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getExtend(null));
    }

    @Test
    public void testGetFileNameNull() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getExtend(null));
    }

    @Test
    public void testGetFileName() {
        assertEquals("test", FileUtils.getFileName(fileName));
        assertEquals("test", FileUtils.getFileName("te st.jpg"));
    }

    @Test
    public void testGetFileNameFromPathBlank() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getFileNameFromPath(""));
    }

    @Test
    public void testGetFileNameFromPathNull() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getFileNameFromPath(null));
    }

    @Test
    public void testGetFileNameFromPath() {
        assertEquals("excel.xls", FileUtils.getFileNameFromPath(path));
        assertEquals("1.pdf", FileUtils.getFileNameFromPath("http://a.com\\2\\1.pdf"));
    }

}