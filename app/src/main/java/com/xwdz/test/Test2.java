package com.xwdz.test;

/**
 * @author huangxingwei(quinn @ 9fens.com)
 * @since 2018/4/2/002
 */
public class Test2 {
    private String code;
    private String message;

    public Test2(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Test2{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
