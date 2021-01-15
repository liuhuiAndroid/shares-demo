package com.sec.shares.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({QuitEvent.QUIT})
@Retention(RetentionPolicy.SOURCE)
public @interface QuitEvent {

    int QUIT = 0;
}
