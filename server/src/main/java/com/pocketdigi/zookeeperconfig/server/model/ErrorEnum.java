package com.pocketdigi.zookeeperconfig.server.model;

/**
 * @author fhp
 * @date 2019-08-08
 */
public enum ErrorEnum {
    //未登录
    UNKNOWN(-1, "未知错误"),
    USER_UN_LOGIN(10000, "未登录"),
    USER_LOGIN_ERROR(10001, "账号或密码错误"),
    CLIENT_PARAM_NULL(40001, "缺少参数"),
    CLIENT_PARAM_ERROR(40002, "参数校验不通过"),
    CLIENT_METHOD_ERROR(40003, "METHOD错误");
    private final int errorCode;
    private final String errorMessage;


    ErrorEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }}
