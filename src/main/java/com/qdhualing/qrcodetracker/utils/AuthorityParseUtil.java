package com.qdhualing.qrcodetracker.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 马鹏昊
 * @desc
 * @date 2018/2/26
 */
public class AuthorityParseUtil {

    /**
     * @param authorityStr 所属部门拥有的权限集合字符串
     * @param extraAuth    额外添加的权限
     * @return
     * @desc 将数据库中查出来的权限字符串解析成module2中的对应功能名集合
     */
    public static List<Integer> parseToFunctionList(String authorityStr, int extraAuth) {
        List<Integer> result = new ArrayList<Integer>();
    	if(authorityStr==null) {
    		return result;
    	}
        String[] strs = authorityStr.split(",");
        for (String s : strs) {
            result.add(Integer.parseInt(s));
        }
        if (extraAuth > 0 && !result.contains(extraAuth))
            result.add(extraAuth);
        return result;
    }

}
