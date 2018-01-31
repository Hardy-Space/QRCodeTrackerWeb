package com.qdhualing.qrcodetracker.dao;

import com.qdhualing.qrcodetracker.bean.DataInputParams;
import com.qdhualing.qrcodetracker.bean.DataResult;
import com.qdhualing.qrcodetracker.bean.WLINResult;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface MainDao {

    //获取物料入库所需录入数据
    DataResult getMaterialInData(String qrcodeId);

    //获取物料出库所需录入数据
    DataResult getMaterialOutData(String qrcodeId);

    //获取半成品入库所需录入数据
    DataResult getHalfProductInData(String qrcodeId);

    //获取成品入库所需录入数据
    DataResult getProductInData(String qrcodeId);

    //获取成品出库所需录入数据
    DataResult getProductOutData(String qrcodeId);

    //获取投料所需录入数据
    DataResult getMaterialThrowData(String qrcodeId);

    //获取物料退库所需录入数据
    DataResult getMaterialReturnData(String qrcodeId);

    int commitMaterialInputedData(DataInputParams params);
}
