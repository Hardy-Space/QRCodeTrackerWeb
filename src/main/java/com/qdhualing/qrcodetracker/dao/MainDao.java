package com.qdhualing.qrcodetracker.dao;

import java.util.List;

import com.qdhualing.qrcodetracker.bean.*;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2018/1/29.
 */

public interface MainDao {

    //获取物料入库所需录入数据
    DataResult getMaterialInData(String qrcodeId);
    //插入入库单的物料委托信息
    int createWLWT_RKD(CreateWLRKDParam rkdpParams);
    //插入入库单的物料信息
    int createWL_RKD(CreateWLRKDParam rkdpParams);
    //插入物料入库信息
    int createWLIN_M(WLINParam wlinParam);
    //根据入库单号查询入库信息
    CreateWLRKDParam getCreateRKDParamByInDh(String InDh);
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
    //物料入库信息提交
    int commitMaterialInputedData(DataInputParams params);
    //获取物料类别数据
    List<PdtSortBean> getPdtSort();
    //获取物料分类数据
    List<HlSortBean> getHlSort();
    //创建物料出库单
    int createWL_CKD(CreateWLCKDParam ckdParam);
    //插入物料委托表记录
    int createWLWT_CKD(CreateWLCKDParam ckdParam);
    //物料出库获取已知数据用于显示不可修改
    WLOutShowDataResult getWLSData(String qrcodeId);
    //查询物料库存表是否已存在记录
    Integer queryWLS(String s);
    //插入物料库存表记录
    int insertWLS(WLINParam wlinParam);
    //物料入库修改库存表数据（数量加）
    int updateWLS(WLINParam wlinParam);
    //select库存表记录
    WLSBean findWLS(String qrCodeId);
    //插入物料出库记录表记录
    int insertWLOUT(WLOutParam wlOutParam);
    //物料出库修改库存表记录（数量减）
    int outUpdateWLS(WLOutParam wlOutParam);
    //查找出库单数据
    CKDWLBean findWL_CKD(String outDh);

    List<UserGroupBean> getUserGroupData();
}
