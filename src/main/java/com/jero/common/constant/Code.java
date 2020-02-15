package com.jero.common.constant;

/**
 * Code码
 *
 * @author zer0
 * @version 1.0
 */
public class Code {

    public static Code ERROR = new Code(0, "系统错误");
    public static Code SUCCESS = new Code(1, "操作成功");
    public static Code ILLEGAL_TOKEN = new Code(1001, "不合法的凭证");
    public static Code ILLEGAL_FILE_TYPE = new Code(1002, "不合法的文件类型");
    public static Code ILLEGAL_FILE_SIZE = new Code(1003, "不合法的文件大小");
    public static Code ILLEGAL_PARAM = new Code(1004, "不合法的参数");
    public static Code UNSUPPORT_IMAGE_TYPE = new Code(1005, "不支持的图片格式");
    public static Code UNSUPPORT_VIDEO_TYPE = new Code(1006, "不支持的视频格式");
    public static Code TEXT_OVER_LIMIT = new Code(1007, "文本超过限制");
    public static Code JSON_ERROR = new Code(1008, "解析JSON内容错误");
    public static Code NOT_FIND_FILE = new Code(1009, "文件不存在");
    public static Code FILE_STREAM_EXCEPTION = new Code(1010, "文件流异常");

    private int code;
    private String msg;

    private Code(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
