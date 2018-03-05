package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.CreateRKDParam;
import com.qdhualing.qrcodetracker.bean.DataInputParams;
import com.qdhualing.qrcodetracker.bean.DataResult;
import com.qdhualing.qrcodetracker.bean.GetNeedInputedDataParams;
import com.qdhualing.qrcodetracker.bean.WLINParam;
import com.qdhualing.qrcodetracker.bean.WLINResult;
import com.qdhualing.qrcodetracker.dao.MainDao;
import com.qdhualing.qrcodetracker.model.FunctionType;
import com.qdhualing.qrcodetracker.utils.TypeParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

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
    //保存物料的入库单信息
    public int createWL_RKD(CreateRKDParam rkdpParams) {
    	int a=mainDao.createWL_RKD(rkdpParams);
    	if(a==1) {
    		return 1;
    	}
    	return 0;
    }
    //保存物料委托的入库单信息
    public int createWLWT_RKD(CreateRKDParam rkdpParams) {
    	int b=mainDao.createWLWT_RKD(rkdpParams);
    	if(b==1) {
    		return 1;
    	}
    	return 0;
    }
    //插入物料入库信息
    public int createWLIN_M(WLINParam wlinParam) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	wlinParam.setlLTime(df.format(System.currentTimeMillis()));
    	wlinParam.setBz(1);
    	int b=mainDao.createWLIN_M(wlinParam);
    	if(b==1) {
    		return 1;
    	}
		return 0;
    }
    //根据入库单号删除对应的物料入库信息
    public int deletWLIN_m(WLINParam wlinParam) {
    	
		return 0;
    }
    //根据单号去查询物料入库信息
    public int getCreateRKDParamByInDh(Long InDh) {
    	CreateRKDParam param=mainDao.getCreateRKDParamByInDh(InDh);
    	if(param!=null) {
    		return 1;
    	}
		return 0;
    };
    
    //根据入库单号查询物料入库信息
    public List<WLINParam> getWLINParamListByInDh(String InDh){
    	List<WLINParam> list=mainDao.getWLINParamListByInDh(InDh);
    	if(list.size()<=0) {
    		return list;
    	}
		return list;
    }
    //根据二维码编号查询物料入库存量的对应记录
    public WLINParam getWLSParamByQRCode(String QRCode_ID) {
    	WLINParam wlinParam=mainDao.getWLSParamByQRCode(QRCode_ID);
    	if(wlinParam!=null) {
    		return wlinParam;
    	}
		return wlinParam;
    };
    //根据二维码编号将数据更新到物料入库存量的对应记录中
    public int updataWLINParamByQRCode(double PCZL,String QRCode_ID) {
    	int a=mainDao.updataWLINParamByQRCode(PCZL, QRCode_ID);
    	if(a==1) {
    		return a;
    	}
    	return a;
    }
    
    public int commitMaterialInputedData(DataInputParams params) {
        return mainDao.commitMaterialInputedData(params);
    }
}
