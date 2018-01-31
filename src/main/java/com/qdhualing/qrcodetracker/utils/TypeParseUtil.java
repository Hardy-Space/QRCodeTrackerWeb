package com.qdhualing.qrcodetracker.utils;

import com.qdhualing.qrcodetracker.model.FunctionType;

/**
 * Created by Administrator on 2018/1/30.
 */
public class TypeParseUtil {

    public static String parseType(int type) {
        switch (type) {
            case FunctionType.MATERIAL_IN:
                return "M_WLIn";
            case FunctionType.MATERIAL_OUT:
                return "M_WLOUT";
            case FunctionType.HALF_PRODUCT_IN:
                return "M_BCPIn";
            case FunctionType.PRODUCT_IN:
                return "M_CPIn";
            case FunctionType.PRODUCT_OUT:
                return "M_CPOut";
            case FunctionType.MATERIAL_THROW:
                return "M_WLTl";
            case FunctionType.MATERIAL_RETURN:
                return "";
        }
        return null;
    }

}
