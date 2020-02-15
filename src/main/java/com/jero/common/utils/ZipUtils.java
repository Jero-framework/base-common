package com.jero.common.utils;

import com.jero.common.bean.FileDirBean;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Zip压缩工具类
 *
 * @author zer0
 * @version 1.0
 */
public class ZipUtils {

    /**
     * 压缩多个文件
     * @param files 要压缩的文件列表
     * @param zipPath 压缩文件的保持路径和文件名
     * @return
     */
    public static String zip(List<String> files, String zipPath){
        if (files == null || files.size() <= 0){
            throw new IllegalArgumentException("将压缩的文件列表不能为空");
        }
        String result = "";

        //获取文件数组的父目录
        List<FileDirBean> waitZipFiles = new ArrayList<FileDirBean>();

        for (int i = 0; i < files.size(); i++){
            File file = new File(files.get(i));
            if (!file.exists()){
                result += "文件\"" + files.get(i) + "\"不存在;\n";
                continue;
            }
            List<String> fileStrs = getFiles(files.get(i));

            for (int j = 0; j < fileStrs.size(); j++) {
                FileDirBean fileDir = new FileDirBean(fileStrs.get(j), file.getParent());
                waitZipFiles.add(fileDir);
            }
        }
        boolean success = compressFileZip(waitZipFiles, zipPath);
        return result.equals("") ? success ?  "压缩成功" : "压缩失败" : result;
    }

    /**
     * 压缩某文件目录
     * @param dir 要压缩的文件目录
     * @param zipPath 压缩文件的保持路径和文件名
     * @return
     */
    public static String zip(String dir, String zipPath){
        List<String> files = new ArrayList<String>(Arrays.asList(new String[]{dir}));
        return zip(files, zipPath);
    }

    /**
     * 解压缩
     * @param zipFilePath
     * @param saveFilePath
     */
    public static void unzip(String zipFilePath, String saveFilePath) {
        if (!saveFilePath.endsWith("\\") && !saveFilePath.endsWith("/")) {
            saveFilePath += File.separator;
        }

        File dir = new File(saveFilePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(zipFilePath);
        if (file.exists()) {
            InputStream is = null;
            ZipArchiveInputStream zais = null;
            try {
                is = new FileInputStream(file);
                zais = new ZipArchiveInputStream(is);
                ArchiveEntry archiveEntry = null;
                while ((archiveEntry = zais.getNextEntry()) != null) {
                    // 获取文件名
                    String entryFileName = archiveEntry.getName();
                    // 构造解压出来的文件存放路径
                    String entryFilePath = saveFilePath + entryFileName;
                    OutputStream os = null;
                    try {
                        // 把解压出来的文件写到指定路径
                        File entryFile = new File(entryFilePath);
                        if (entryFileName.endsWith("/")) {
                            entryFile.mkdirs();
                        } else {
                            os = new BufferedOutputStream(new FileOutputStream(
                                    entryFile));
                            byte[] buffer = new byte[1024];
                            int len = -1;
                            while ((len = zais.read(buffer)) != -1) {
                                os.write(buffer, 0, len);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (os != null) {
                            os.flush();
                            os.close();
                        }
                    }

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (zais != null) {
                        zais.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 递归获取当前目录的所有文件
     * @param dir 要遍历的目录
     * @return
     */
    private static List<String> getFiles(String dir){
        List<String> lstFiles = null;
        if(lstFiles == null){
            lstFiles = new ArrayList<String>();
        }
        File file = new File(dir);
        if (file.isDirectory()){
            File [] files = file.listFiles();
            for(File f : files){
                if(f.isDirectory()){
                    lstFiles.add(f.getAbsolutePath());
                    lstFiles.addAll(getFiles(f.getAbsolutePath()));
                }else{
                    String str =f.getAbsolutePath();
                    lstFiles.add(str);
                }
            }
        }else{
            lstFiles.add(dir);
        }
        return lstFiles;
    }

    /**
     * 压缩文件成zip
     * @param waitZipFiles 需要压缩的文件
     * @param zipFilePath 压缩后的路径
     * @return
     */
     private static boolean compressFileZip(List<FileDirBean> waitZipFiles, String zipFilePath){
        if (waitZipFiles == null || waitZipFiles.size() <= 0){
            return false;
        }

        if (StringUtils.isBlank(zipFilePath)){
            throw new IllegalArgumentException("压缩文件名不能为空");
        }

        ZipArchiveOutputStream zaos = null;
        try{
            File zipFile = new File(zipFilePath);
            zaos = new ZipArchiveOutputStream(zipFile);
            zaos.setUseZip64(Zip64Mode.AsNeeded);
            for(int i=0; i < waitZipFiles.size(); i++){
                File file = new File(waitZipFiles.get(i).getFileName());
                if(file != null){
                    String name = getFilePathName(waitZipFiles.get(i).getDirName() ,waitZipFiles.get(i).getFileName());
                    ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, name);
                    zaos.putArchiveEntry(zipArchiveEntry);
                    if (file.isDirectory()){
                        continue;
                    }
                    InputStream is = null;
                    try{
                        is = new BufferedInputStream(new FileInputStream(file));
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1){
                            zaos.write(buffer, 0, len);
                        }
                        zaos.closeArchiveEntry();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if (is != null){
                            is.close();
                        }
                    }
                }
            }
            zaos.finish();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(zaos != null) {
                    zaos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getFilePathName(String dir,String path){
        dir = FileUtils.uniformSeparator(dir);
        path = FileUtils.uniformSeparator(path);

        String p = "";
        if (dir.endsWith("/")){
            p = path.replace(dir, "");
        }else{
            p = path.replace(dir+ "/", "");
        }

        p = p.replace("\\", "/");
        return p;
    }

}
