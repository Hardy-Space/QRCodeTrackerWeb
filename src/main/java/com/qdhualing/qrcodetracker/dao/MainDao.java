package com.qdhualing.qrcodetracker.dao;

import java.util.List;

import com.qdhualing.qrcodetracker.bean.CreateRKDParam;
import com.qdhualing.qrcodetracker.bean.DataInputParams;
import com.qdhualing.qrcodetracker.bean.DataResult;
import com.qdhualing.qrcodetracker.bean.WLINParam;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface MainDao {

    //获取物料入库所需录入数据
    DataResult getMaterialInData(String qrcodeId);
    //插入入库单的物料委托信息
    int createWLWT_RKD(CreateRKDParam rkdpParams);
    //插入入库单的物料信息
    int createWL_RKD(CreateRKDParam rkdpParams);
    //插入物料入库信息
    int createWLIN_M(WLINParam wlinParam);
    //根据入库单号查询入库信息
    CreateRKDParam getCreateRKDParamByInDh(Long InDh);
    //根据入库单号删除对应的入库信息
    int delWLIn_M(String InDh);
    //根据入库单号删除对应的入库单委托信息
    int delWLWT_RKD(String InDh);
    //根据入库单号删除对应的入库单信息
    int delWL_RKD(String InDh);
    //根据入库单号查询物料入库记录(可能为多条)
    List<WLINParam> getWLINParamListByInDh(String InDh);
    //根据二维码编号查询物料入库存量的对应记录
    WLINParam getWLSParamByQRCode(String QRCode_ID);
    //根据二维码编号将物料存量进行更新
    int updataWLINParamByQRCode(double PCZL,String QRCode_ID);

    int commitMaterialInputedData(DataInputParams params);
}
