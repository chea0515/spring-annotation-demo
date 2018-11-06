package com.cc.anno.demo.test;

import java.util.regex.Pattern;

public class CommTest {
    public static void main(String[] args) {
        // iima4iuhnf1x69r9jm5 7b8c8086e08b412a80e0932c3c29ce48
        String str = "7b8c8086e08b412a80e0932c3c29ce48";
        String regex = "^[A-Za-z0-9]{0,32}$";
        boolean y = Pattern.matches(regex, str);
        System.err.println(y);
    }
}
