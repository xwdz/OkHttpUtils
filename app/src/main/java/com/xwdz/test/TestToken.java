/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.xwdz.test;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestToken  {

    public final int id;
    public final String username;
    public final String token;

    public TestToken(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}
