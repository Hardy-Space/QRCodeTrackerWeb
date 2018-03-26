package com.qdhualing.qrcodetracker.utils;

import com.qdhualing.qrcodetracker.bean.BigCpBean;

/**
 * @author 马鹏昊
 * @desc 本项目内的一些共有方法
 * @date 2018/3/23
 */
public class ProjectUtil {

    //填充大包装包含的小包装的二维码id
    public static BigCpBean getUpdateCPS2Data(BigCpBean bigCpBean, int index, Long nextQrCodeId) {
        switch (index){
            case 1:
                bigCpBean.setSmlPk1(""+nextQrCodeId);
                break;
            case 2:
                bigCpBean.setSmlPk2(""+nextQrCodeId);
                break;
            case 3:
                bigCpBean.setSmlPk3(""+nextQrCodeId);
                break;
            case 4:
                bigCpBean.setSmlPk4(""+nextQrCodeId);
                break;
            case 5:
                bigCpBean.setSmlPk5(""+nextQrCodeId);
                break;
            case 6:
                bigCpBean.setSmlPk6(""+nextQrCodeId);
                break;
            case 7:
                bigCpBean.setSmlPk7(""+nextQrCodeId);
                break;
            case 8:
                bigCpBean.setSmlPk8(""+nextQrCodeId);
                break;
            case 9:
                bigCpBean.setSmlPk9(""+nextQrCodeId);
                break;
            case 10:
                bigCpBean.setSmlPk10(""+nextQrCodeId);
                break;
            case 11:
                bigCpBean.setSmlPk11(""+nextQrCodeId);
                break;
            case 12:
                bigCpBean.setSmlPk12(""+nextQrCodeId);
                break;
            case 13:
                bigCpBean.setSmlPk13(""+nextQrCodeId);
                break;
            case 14:
                bigCpBean.setSmlPk14(""+nextQrCodeId);
                break;
            case 15:
                bigCpBean.setSmlPk15(""+nextQrCodeId);
                break;
            case 16:
                bigCpBean.setSmlPk16(""+nextQrCodeId);
                break;
            case 17:
                bigCpBean.setSmlPk17(""+nextQrCodeId);
                break;
            case 18:
                bigCpBean.setSmlPk18(""+nextQrCodeId);
                break;
            case 19:
                bigCpBean.setSmlPk19(""+nextQrCodeId);
                break;
            case 20:
                bigCpBean.setSmlPk20(""+nextQrCodeId);
                break;
            case 21:
                bigCpBean.setSmlPk21(""+nextQrCodeId);
                break;
            case 22:
                bigCpBean.setSmlPk22(""+nextQrCodeId);
                break;
            case 23:
                bigCpBean.setSmlPk23(""+nextQrCodeId);
                break;
            case 24:
                bigCpBean.setSmlPk24(""+nextQrCodeId);
                break;
            case 25:
                bigCpBean.setSmlPk25(""+nextQrCodeId);
                break;
            case 26:
                bigCpBean.setSmlPk26(""+nextQrCodeId);
                break;
            case 27:
                bigCpBean.setSmlPk27(""+nextQrCodeId);
                break;
            case 28:
                bigCpBean.setSmlPk28(""+nextQrCodeId);
                break;
            case 29:
                bigCpBean.setSmlPk29(""+nextQrCodeId);
                break;
            case 30:
                bigCpBean.setSmlPk30(""+nextQrCodeId);
                break;
            case 31:
                bigCpBean.setSmlPk31(""+nextQrCodeId);
                break;
            case 32:
                bigCpBean.setSmlPk32(""+nextQrCodeId);
                break;
            case 33:
                bigCpBean.setSmlPk33(""+nextQrCodeId);
                break;
            case 34:
                bigCpBean.setSmlPk34(""+nextQrCodeId);
                break;
            case 35:
                bigCpBean.setSmlPk35(""+nextQrCodeId);
                break;
            case 36:
                bigCpBean.setSmlPk36(""+nextQrCodeId);
                break;
            case 37:
                bigCpBean.setSmlPk37(""+nextQrCodeId);
                break;
            case 38:
                bigCpBean.setSmlPk38(""+nextQrCodeId);
                break;
            case 39:
                bigCpBean.setSmlPk39(""+nextQrCodeId);
                break;
            case 40:
                bigCpBean.setSmlPk40(""+nextQrCodeId);
                break;
            case 41:
                bigCpBean.setSmlPk41(""+nextQrCodeId);
                break;
            case 42:
                bigCpBean.setSmlPk42(""+nextQrCodeId);
                break;
            case 43:
                bigCpBean.setSmlPk43(""+nextQrCodeId);
                break;
            case 44:
                bigCpBean.setSmlPk44(""+nextQrCodeId);
                break;
            case 45:
                bigCpBean.setSmlPk45(""+nextQrCodeId);
                break;
            case 46:
                bigCpBean.setSmlPk46(""+nextQrCodeId);
                break;
            case 47:
                bigCpBean.setSmlPk47(""+nextQrCodeId);
                break;
            case 48:
                bigCpBean.setSmlPk48(""+nextQrCodeId);
                break;
            case 49:
                bigCpBean.setSmlPk49(""+nextQrCodeId);
                break;
            case 50:
                bigCpBean.setSmlPk50(""+nextQrCodeId);
                break;
        }
        bigCpBean.setNowNum(index);
        return bigCpBean;
    }
}
