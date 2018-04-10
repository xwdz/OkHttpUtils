/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.xwdz.test;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestToken implements Serializable {

    public final int id;
    public final String username;
    public final String cid;
    public final String utype;
    public final String phone;
    public final String email;
    public final String ustatus;
    public final String park_codes;
    public final String power;
    public final String is_new_user;
    public final String is_todo_binding;
    public final String token;
    public String theme;
    public List<ParkInfo> park_info;

    public TestToken(String token, String power, String parkCodes,
                     String ustatus, String email, String phone, String utype,
                     String cid, String username, int id, String theme, String is_new_user, String is_todo_binding,
                     List<ParkInfo> park_info) {
        this.token = token;
        this.is_new_user = is_new_user;
        this.is_todo_binding = is_todo_binding;
        this.power = power;
        this.park_codes = parkCodes;
        this.ustatus = ustatus;
        this.email = email;
        this.phone = phone;
        this.utype = utype;
        this.cid = cid;
        this.username = username;
        this.id = id;
        this.theme = theme;
        this.park_info = park_info;
    }

    @Override
    public String toString() {
        return "TestToken{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", cid='" + cid + '\'' +
                ", utype='" + utype + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", ustatus='" + ustatus + '\'' +
                ", park_codes='" + park_codes + '\'' +
                ", power='" + power + '\'' +
                ", is_new_user='" + is_new_user + '\'' +
                ", is_todo_binding='" + is_todo_binding + '\'' +
                ", token='" + token + '\'' +
                ", theme='" + theme + '\'' +
                ", park_info=" + park_info +
                '}';
    }

    public static class User {
        @SerializedName("username")
        private String userName;
        @SerializedName("password")
        private String password;

        public User(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public User(String userName) {
            this.userName = userName;
        }
    }

    public static class ItemPower {
        @SerializedName("name")
        public final String name;
        @SerializedName("text")
        public final String text;
        @SerializedName("children")
        public final List<Children> children;

        public ItemPower(String name, String text, List<Children> children) {
            this.name = name;
            this.text = text;
            this.children = children;
        }
    }

    public static class Children {
        @SerializedName("name")
        public final String name;
        @SerializedName("text")
        public final String text;

        public Children(String name, String text) {
            this.name = name;
            this.text = text;
        }
    }

    public static class ParkInfo implements Parcelable {

        @SerializedName("park_code")
        public final String parkCode;
        @SerializedName("parking_name")
        public final String parkingName;

        public ParkInfo(String parkCode, String parkingName) {
            this.parkCode = parkCode;
            this.parkingName = parkingName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.parkCode);
            dest.writeString(this.parkingName);
        }

        protected ParkInfo(Parcel in) {
            this.parkCode = in.readString();
            this.parkingName = in.readString();
        }

        public static final Parcelable.Creator<ParkInfo> CREATOR = new Parcelable.Creator<ParkInfo>() {
            @Override
            public ParkInfo createFromParcel(Parcel source) {
                return new ParkInfo(source);
            }

            @Override
            public ParkInfo[] newArray(int size) {
                return new ParkInfo[size];
            }
        };
    }
}
