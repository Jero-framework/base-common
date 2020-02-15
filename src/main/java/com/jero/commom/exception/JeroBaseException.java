package com.jero.commom.exception;

import com.jero.commom.constant.Code;

public class JeroBaseException extends RuntimeException {

    private static final long serialVersionUID = 3785352450518033758L;

    /**
     * 错误编码
     */
    private int errorCode;

    public JeroBaseException(String message) {
        this(-1, message);
    }

    public JeroBaseException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public JeroBaseException(Code code){
        this(code.getCode(), code.getMsg());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


}
