package com.sec.shares.app;

public final class ApiException extends RuntimeException {

    public static final int NET_SUCCESS = 0;
    public static final int EMPTY = -1;
    public static final int NET_ERROR = 1000;

    private final int mCode;

    public ApiException(int code) {
        this("", code);
    }

    public ApiException(String message, int code) {
        super(message);
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }
}
