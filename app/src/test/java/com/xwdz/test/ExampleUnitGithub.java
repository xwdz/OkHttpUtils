package com.xwdz.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitGithub {
    @Test
    public void addition_isCorrect() {
        File file = new File("pause");
        System.out.println("name = " + file.getName());
    }
}