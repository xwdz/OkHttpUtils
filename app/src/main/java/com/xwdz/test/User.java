package com.xwdz.test;

/**
 * @author 黄兴伟 (xwd9989@gamil.com)
 * @since 2018/4/6
 */
public class User {
    private String code;
    private String message;
    private String data;

    public User(String code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
