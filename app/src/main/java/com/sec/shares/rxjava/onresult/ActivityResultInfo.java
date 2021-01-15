package com.sec.shares.rxjava.onresult;

import android.content.Intent;

public class ActivityResultInfo {

    private final int requestCode;
    private final int resultCode;
    private final Intent data;

    ActivityResultInfo(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
