package com.qdhualing.qrcodetracker.utils;

import java.util.Random;

/**
 * @author 马鹏昊
 * @desc
 * @date 2018/3/7
 */
public class RandomUtil {

    public static long getRandomLong(){
        Random rd = new Random();
        return rd.nextLong();
    }

}
