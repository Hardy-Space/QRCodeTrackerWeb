package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.bean.CreateWLRKDParam;
import com.qdhualing.qrcodetracker.dao.MainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

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
    public int createWL_RKD(CreateWLRKDParam rkdpParams) {
    	int a=mainDao.createWL_RKD(rkdpParams);
    	if(a==1) {
    		return 1;
    	}
    	return 0;
    }
    //保存物料委托的入库单信息
    public int createWLWT_RKD(CreateWLRKDParam rkdpParams) {
    	int b=mainDao.createWLWT_RKD(rkdpParams);
    	if(b==1) {
    		return 1;
    	}
    	return 0;
    }
    //插入物料入库信息
    public int createWLIN_M(WLINParam wlinParam) {
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
    public int getCreateRKDParamByInDh(String InDh) {
    	CreateWLRKDParam param=mainDao.getCreateRKDParamByInDh(InDh);
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

	public PdtSortResult getPdtSort() {
    	List<PdtSortBean> beans =  mainDao.getPdtSort();
		PdtSortResult pdtSortResult = new PdtSortResult();
		pdtSortResult.setSortBeans(beans);
		return pdtSortResult;
	}

	public HlSortResult getHlSort() {
		List<HlSortBean> beans =  mainDao.getHlSort();
		HlSortResult hlSortResult = new HlSortResult();
		hlSortResult.setHlSortBeans(beans);
		return hlSortResult;
	}

	public int createWL_CKD(CreateWLCKDParam ckdParam) {
		int a=mainDao.createWL_CKD(ckdParam);
		return a;
	}

	public int createWLWT_CKD(CreateWLCKDParam ckdParam) {
		int a=mainDao.createWLWT_CKD(ckdParam);
		return a;
	}

	public WLOutShowDataResult getWLSData(String qrcodeId) {
		WLOutShowDataResult result = null;
		result = mainDao.getWLSData(qrcodeId);
		return result;
	}

	public int queryWLS(String s) {
    	Integer bb = mainDao.queryWLS(s);
		return bb==null?0:bb;
	}

	public int insertWLS(WLINParam wlinParam) {
		return mainDao.insertWLS(wlinParam);
	}

	public int updateWLS(WLINParam wlinParam) {
		return mainDao.updateWLS(wlinParam);
	}

	public WLSBean findWLS(String qrCodeId) {
    	WLSBean wlsBean = null;
    	wlsBean = mainDao.findWLS(qrCodeId);
		return wlsBean;
	}

	public int insertWLOUT(WLOutParam wlOutParam) {
    	wlOutParam.setBz(1);
		int a=mainDao.insertWLOUT(wlOutParam);
		return a;
	}

	public int outUpdateWLS(WLOutParam wlOutParam) {
		return mainDao.outUpdateWLS(wlOutParam);
	}

	public CKDWLBean findWL_CKD(String outDh) {
		CKDWLBean ckdwlBean = null;
		ckdwlBean = mainDao.findWL_CKD(outDh);
		return ckdwlBean;
	}

	public UserGroupResult getUserGroupData() {
		List<UserGroupBean> beans =  mainDao.getUserGroupData();
		UserGroupResult userGroupResult = new UserGroupResult();
		userGroupResult.setGroupBeanList(beans);
		return userGroupResult;
	}
}
