package com.jero.common.bean;

/**
 * 描述文件名和文件父类的bean
 *
 * @author zer0
 * @version 1.0
 */
public class FileDirBean {

    private String fileName;

    /**
     * 目录名
     */
    private String dirName;

    public FileDirBean(String fileName, String dirName){
        this.fileName = fileName;
        this.dirName = dirName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }
}
