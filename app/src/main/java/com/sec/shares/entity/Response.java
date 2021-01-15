package com.sec.shares.entity;

import com.google.gson.annotations.SerializedName;

public class Response<T> {

    @SerializedName("ResultNo")
    private int mCode;
    @SerializedName("Message")
    private String mMessage;
    @SerializedName("Total")
    private int total;
    @SerializedName("Result")
    private T content;

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
