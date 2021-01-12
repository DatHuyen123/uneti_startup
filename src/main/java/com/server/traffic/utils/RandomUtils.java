package com.server.traffic.utils;

import java.util.Random;

public class RandomUtils {

    public static String randomCode(){
        Random rand = new Random();
        Integer n = rand.nextInt(899999);
        n = n + 100000;
        return n.toString();
    }

}
