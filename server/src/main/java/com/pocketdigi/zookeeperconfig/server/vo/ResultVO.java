package com.pocketdigi.zookeeperconfig.server.vo;

import com.pocketdigi.zookeeperconfig.server.model.ErrorEnum;

/**
 * 接口结果
 */
public class ResultVO<T> implements java.io.Serializable{
    private String message;
    private Integer error;
    private T data;

    public static <T> ResultVO<T> wrapSuccess(T data) {
        ResultVO<T> result = new ResultVO<>();
        result.data = data;
        result.error =0;
        result.message ="成功";
        return result;
    }

    public static <T> ResultVO<T> wrapError(int code, String msg) {
        ResultVO<T> result = new ResultVO<>();
        result.error =code;
        result.message =msg;
        return result;
    }

    public  static <T> ResultVO<T> wrapError(ErrorEnum errorEnum) {
        ResultVO<T> result = new ResultVO<>();
        result.error =errorEnum.getErrorCode();
        result.message =errorEnum.getErrorMessage();
        return result;
    }

    public static <T> ResultVO<T> wrapSuccess() {
        return wrapSuccess(null);
    }

    public String getMessage() {
        return message;
    }

    public int getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return error==0;
    }

}