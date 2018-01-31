package com.qdhualing.qrcodetracker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 马鹏昊
 * @desc
 * @date 2018/1/31
 */
public class AppInputPropertyHolder {

    //持有物料入库所需App填入数据
    public static Map<String,String> mWlinPropertyMap = new HashMap<String, String>();

    //持有物料出库所需App填入数据
    public static Map<String,String> mWloutPropertyMap = new HashMap<String, String>();

    //持有投料所需App填入数据
    public static Map<String,String> mWltlPropertyMap = new HashMap<String, String>();

    //持有半成品入库所需App填入数据
    public static Map<String,String> mBcpinPropertyMap = new HashMap<String, String>();

    //持有成品入库所需App填入数据
    public static Map<String,String> mCpinPropertyMap = new HashMap<String, String>();

    //持有成品出库所需App填入数据
    public static Map<String,String> mCpoutPropertyMap = new HashMap<String, String>();

    //持有物料退库所需App填入数据
    public static Map<String,String> mWlReturnPropertyMap = new HashMap<String, String>();

    public static void initWlinPropertyMap(){
        mWlinPropertyMap.put("ProductName","物料名称");
        mWlinPropertyMap.put("YLPC","原料批次");
        mWlinPropertyMap.put("PCZL","批次总量");
        mWlinPropertyMap.put("LL_Time","来料时间");
        mWlinPropertyMap.put("DWZL","单位重量(kg)");
        mWlinPropertyMap.put("SHL","数量");
        mWlinPropertyMap.put("XH","序号");
        mWlinPropertyMap.put("CHD","产地");
        mWlinPropertyMap.put("GG","规格");
        mWlinPropertyMap.put("CZY","操作员");
        mWlinPropertyMap.put("InDh","入库单号");
        mWlinPropertyMap.put("Company","单位");
        mWlinPropertyMap.put("YuJing","预警数量");
    }
    public static void initWloutPropertyMap(){

    }
    public static void initWltlPropertyMap(){

    }
    public static void initBcpinPropertyMap(){

    }
    public static void initCpinPropertyMap(){

    }
    public static void initCpoutPropertyMap(){

    }
    public static void initWlReturnPropertyMap(){

    }

}
