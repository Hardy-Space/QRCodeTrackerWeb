package com.qdhualing.qrcodetracker.utils;

import com.qdhualing.qrcodetracker.bean.ActionResult;

/**
 * Created by Administrator on 2018/1/27.
 */
public class ActionResultUtils {

    public static ActionResult setResultMsg(ActionResult result,int code,String errorTipMsg){
        result.setCode(code);
        result.setMessage(errorTipMsg);
        return result;
    }

}
