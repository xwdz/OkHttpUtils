package com.xwdz.test;


import org.junit.Test;

import java.io.File;

/**
 * Example local unit parser, which will execute on the development machine (host).
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