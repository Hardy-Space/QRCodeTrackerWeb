package com.qdhualing.qrcodetracker.utils;

import com.qdhualing.qrcodetracker.bean.LoginParams;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MPH on 2018/1/29.
 */
public class ParamsUtils {

    public static <T>T handleParams(String jsonStr,Class<T> bean){
        com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
        com.alibaba.fastjson.JSONObject paramsJson = jo.getJSONObject("params");

        return (T) JSONUtils.toBean(paramsJson,bean);
    }

    public static <T, D> T handleListParams(String jsonStr,Class<T> mainClass,String detailName,Class<D> detailClass){
        com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
        com.alibaba.fastjson.JSONObject paramsJson = jo.getJSONObject("params");
        String jsonParamsStr = paramsJson.toJSONString();

        return (T) JSONUtils.toBean(jsonParamsStr,mainClass,detailName,detailClass);
    }

}
