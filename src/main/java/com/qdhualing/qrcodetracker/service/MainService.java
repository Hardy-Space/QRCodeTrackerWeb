package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.DataInputParams;
import com.qdhualing.qrcodetracker.bean.DataResult;
import com.qdhualing.qrcodetracker.bean.GetNeedInputedDataParams;
import com.qdhualing.qrcodetracker.bean.WLINResult;
import com.qdhualing.qrcodetracker.dao.MainDao;
import com.qdhualing.qrcodetracker.model.FunctionType;
import com.qdhualing.qrcodetracker.utils.TypeParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/1/29.
 */
@Service
public class MainService {

    @Autowired
    private MainDao mainDao;

    public DataResult getMaterialInInputedData(GetNeedInputedDataParams params) {

        DataResult result = mainDao.getMaterialInData(params.getId());
        return result;

    }
    public WLINResult getMaterialOutInputedData(GetNeedInputedDataParams params) {

//        WLINResult result = mainDao.getMaterialOutData(params.getId());
        return null;

    }
    public WLINResult getHalfProductInInputedData(GetNeedInputedDataParams params) {

//        WLINResult result = mainDao.getHalfProductInData(params.getId());
        return null;

    }
    public WLINResult getProductInInputedData(GetNeedInputedDataParams params) {

//        WLINResult result = mainDao.getProductInData(params.getId());
        return null;

    }
    public WLINResult getProductOutInputedData(GetNeedInputedDataParams params) {

//        WLINResult result = mainDao.getProductOutData(params.getId());
        return null;

    }
    public WLINResult getMaterialThrowInputedData(GetNeedInputedDataParams params) {

//        WLINResult result = mainDao.getMaterialThrowData(params.getId());
        return null;

    }
    public WLINResult getMaterialReturnInputedData(GetNeedInputedDataParams params) {

//        WLINResult result = mainDao.getMaterialReturnData(params.getId());
        return null;

    }

    public int commitMaterialInputedData(DataInputParams params) {
        return mainDao.commitMaterialInputedData(params);
    }
}
