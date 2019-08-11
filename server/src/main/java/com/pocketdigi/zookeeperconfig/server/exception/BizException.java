package com.pocketdigi.zookeeperconfig.server.exception;


import com.pocketdigi.zookeeperconfig.server.model.ErrorEnum;


public class BizException extends RuntimeException {


    private Integer code = ErrorEnum.UNKNOWN.getErrorCode();

    public BizException(String message) {
        super(message);
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BizException(ErrorEnum errorEnum) {
        super(errorEnum.getErrorMessage());
        this.code=errorEnum.getErrorCode();
    }

}
