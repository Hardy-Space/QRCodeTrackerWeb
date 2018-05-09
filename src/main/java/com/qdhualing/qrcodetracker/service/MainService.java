package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.bean.CreateWLRKDParam;
import com.qdhualing.qrcodetracker.dao.MainDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	public int insertWLTempS(WLOutParam wlOutParam) {
		return mainDao.insertWLTempS(wlOutParam);
	}

	public int findWLTempS(String qrCodeId) {
		return mainDao.findWLTempS(qrCodeId);
	}

	public int updateWLTempS(WLOutParam wlOutParam) {
		return mainDao.updateWLTempS(wlOutParam);
	}

	public int createWL_TKD(CreateWLTKDParam tkdParam) {
		return mainDao.createWL_TKD(tkdParam);
	}

	public WLTKShowDataResult getWLTempSData(String qrcodeId) {
		WLTKShowDataResult result = null;
		result = mainDao.getWLTempSData(qrcodeId);
		return result;
	}

	public TKDWLBean findWL_TKD(String outDh) {
		return mainDao.findWL_TKD(outDh);
	}

	public WLTempSBean getWLTempS(String qrCodeId) {
		return mainDao.getWLTempS(qrCodeId);
	}

	public int insertWLBk(WLTKParam wlTKParam) {
		return mainDao.insertWLBk(wlTKParam);
	}

	public int updateWLSByTk(WLTKParam wlTKParam) {
		return mainDao.updateWLSByTk(wlTKParam);
	}

	public int deleteFromWLTempS(String qrCodeId) {
		return mainDao.deleteFromWLTempS(qrCodeId);
	}

	public int updateWLTempSByTk(WLTKParam wlTKParam) {
		return mainDao.updateWLTempSByTk(wlTKParam);
	}

	public int insertWLTl(WLThrowParam wlTLParam) {
		return mainDao.insertWLTl(wlTLParam);
	}

	public int updateWLTempSByTl(WLThrowParam wlTLParam) {
		return mainDao.updateWLTempSByTl(wlTLParam);
	}

	public GXResult getGXData(String[] cjIdArray) {
		List<GXBean> beans =  mainDao.getGXData(cjIdArray);
		GXResult result = new GXResult();
		result.setGxBeans(beans);
		return result;
	}

	public CJResult getCJData() {
		List<CJBean> beans =  mainDao.getCJData();
		CJResult result = new CJResult();
		result.setCjBeans(beans);
		return result;
	}

	public int createBCP_RKD(CreateBCPRKDParam rkdParam) {
		return  mainDao.createBCP_RKD(rkdParam);
	}

	public SXYLResult getSXYLData(int gxId) {
    	//获取该工序下的物料
		List<TLYLBean> beans1 =  mainDao.getWLTLDataByGxId(gxId);
		//获取该工序下的半成品
		List<TLYLBean> beans2 =  mainDao.getBCPTLDataByGxId(gxId);
		List<TLYLBean> bb = new ArrayList<TLYLBean>();
		bb.addAll(beans1);
		bb.addAll(beans2);
		SXYLResult result = new SXYLResult();
		result.setTlylList(bb);
		return result;
	}

	public int getWLTLDataCount(String qrcodeId) {
    	return mainDao.getWLTLDataCount(qrcodeId);
	}

	public int updateWLTl(WLThrowParam wlTLParam) {
    	return mainDao.updateWLTl(wlTLParam);
	}

	public int insertBCPIn(BCPINParam bcpInParam) {
		return mainDao.insertBCPIn(bcpInParam);
	}

	public int findBCPTempS(String qrCodeID) {
		return mainDao.findBCPTempS(qrCodeID);
	}

	public int insertBCPTempS(BCPINParam bcpInParam) {
		return mainDao.insertBCPTempS(bcpInParam);
	}

	public int updateBCPTempS(BCPINParam bcpInParam) {
		return mainDao.updateBCPTempS(bcpInParam);
	}

	public BcpThrowShowDataResult getBcpTempSData(String qrcodeId) {
		BcpThrowShowDataResult result = null;
		result = mainDao.getBcpTempSData(qrcodeId);
		return result;
	}

	public BCPTempSBean getBcpTempS(String qrcodeId) {
		return mainDao.getBcpTempS(qrcodeId);
	}

	public int getBcpTLDataCount(String qrcodeId) {
		return mainDao.getBcpTLDataCount(qrcodeId);
	}

	public int insertBcpTl(BcpThrowParam bcpTLParam) {
		return mainDao.insertBcpTl(bcpTLParam);
	}

	public int updateBcpTl(BcpThrowParam bcpTLParam) {
		return mainDao.updateBcpTl(bcpTLParam);
	}

	public int deleteFromBcpTempS(String qrcodeId) {
		return mainDao.deleteFromBcpTempS(qrcodeId);
	}

	public int updateBcpTempSByTl(BcpThrowParam bcpTLParam) {
		return mainDao.updateBcpTempSByTl(bcpTLParam);
	}

	public int createBCP_TKD(CreateBCPTKDParam param) {
		return mainDao.createBCP_TKD(param);
	}

	public BCPTKShowDataResult getBCPTKShowData(String qrcodeId) {
		BCPTKShowDataResult result = null;
		result = mainDao.getBCPTKShowData(qrcodeId);
		return result;
	}

	public TKDBCPBean getTKDBCPBean(String backDh) {
		return mainDao.getTKDBCPBean(backDh);
	}

	public int insertBCPBk(BCPTKParam param) {
		return mainDao.insertBCPBk(param);
	}

	public int getBCPSCount(String qrCodeId) {
		return mainDao.getBCPSCount(qrCodeId);
	}

	public int insertBCPS(BCPTKParam param) {
		return mainDao.insertBCPS(param);
	}

	public int updateBCPSByTk(BCPTKParam param) {
		return mainDao.updateBCPSByTk(param);
	}

	public int updateBCPTempSByBCPTk(BCPTKParam param) {
		return mainDao.updateBCPTempSByBCPTk(param);
	}

	public int createBCP_CKD(CreateBCPCKDParam param) {
		return mainDao.createBCP_CKD(param);
	}

	public int insertCPIn2(BigCPINParam inParam) {
		return mainDao.insertCPIn2(inParam);
	}

	public int findCPS2(String qrCodeId) {
		return mainDao.findCPS2(qrCodeId);
	}

	public int insertCPS2(BigCPINParam inParam) {
		return mainDao.insertCPS2(inParam);
	}

	public BigCpResult getBigCpData() {
		List<BigCpBean> beans =  mainDao.getBigCpData();
		BigCpResult result = new BigCpResult();
		result.setBeans(beans);
		return result;
	}

	public int findCPS(String qrCodeId) {
		return mainDao.findCPS(qrCodeId);
	}

	public int insertCPS(SmallCPINParam inParam) {
		return mainDao.insertCPS(inParam);
	}

	public int updateCPS(SmallCPINParam inParam) {
		return mainDao.updateCPS(inParam);
	}

	public int insertCPIn(SmallCPINParam inParam) {
		return mainDao.insertCPIn(inParam);
	}

	public int updateCPS2(BigCpBean bigCpBean) {
		return mainDao.updateCPS2(bigCpBean);
	}

	public BigCpBean getCPS2(String bigCpQrId) {
		return mainDao.getCPS2(bigCpQrId);
	}

	public int updateCPIn2(BigCpBean bigCpBean) {
		return mainDao.updateCPIn2(bigCpBean);
	}

	public int insertCPOut(BigCpOutParam param) {
		return mainDao.insertCPOut(param);
	}

	public int deleteCPS2ByQrId(String qrCodeId) {
		return mainDao.deleteCPS2ByQrId(qrCodeId);
	}

	public int deleteCPSByCps2QrId(String qrCodeId) {
		return mainDao.deleteCPSByCps2QrId(qrCodeId);
	}

	public BigCpOutGetDataResult getCP2ShowData(String qrCodeId) {
		return mainDao.getCP2ShowData(qrCodeId);
	}

	public SmallCpOutGetDataResult getSmallCpOutData(String qrCodeId) {
		return mainDao.getSmallCpOutData(qrCodeId);
	}

	public SmallCpBean getCPS(String qrCodeId) {
		return mainDao.getCPS(qrCodeId);
	}

	public int deleteCPSByQrId(String qrCodeId) {
		return mainDao.deleteCPSByQrId(qrCodeId);
	}

	public int insertCPOutBySmallParam(SmallCpOutParam param) {
		return mainDao.insertCPOutBySmallParam(param);
	}

	public List<WlTrackResult> getWlInData(String qrcodeId) {
		return mainDao.getWlInData(qrcodeId);
	}

	public List<BCPINParam> getBcpInData(String qrCodeId) {
		return mainDao.getBcpInData(qrCodeId);
	}

	public String getPdtSortBySortId(String sortID) {
		return mainDao.getPdtSortBySortId(sortID);
	}

	public List<ComponentBean> getComponentBeansFromBcp(List<String> ylList) {
		return mainDao.getComponentBeansFromBcp(ylList);
	}

	public List<ComponentBean> getComponentBeansFromWl(List<String> ylList) {
		return mainDao.getComponentBeansFromWl(ylList);
	}

	public List<CPINParam> getSmallCpInData(String qrCodeId) {
		return mainDao.getSmallCpInData(qrCodeId);
	}

	public List<BigCpBean> getBigCpIn2(String qrCodeId) {
		return mainDao.getBigCpIn2(qrCodeId);
	}

	public String getShrFromWlRkd(String dh) {
		return mainDao.getShrFromWlRkd(dh);
	}

	public String getFhrFromWlCkd(String dh) {
		return mainDao.getFhrFromWlCkd(dh);
	}


	public String getShrFromWlTkd(String dh) {
		return mainDao.getShrFromWlTkd(dh);
	}

	public String getShrFromBcpRkd(String dh) {
		return mainDao.getShrFromBcpRkd(dh);
	}

	public String getShrFromBcpTkd(String dh) {
		return mainDao.getShrFromBcpTkd(dh);
	}

	public String getFhrFromBcpCkd(String dh) {
		return mainDao.getFhrFromBcpCkd(dh);
	}

	public List<WlRkdBean> getWlRkNonCheckData(String realName) {
		return mainDao.getWlRkNonCheckData(realName);
	}

	public List<WlCkdBean> getWlCkNonCheckData(String realName) {
		return mainDao.getWlCkNonCheckData(realName);
	}

	public List<WlTkdBean> getWlTkNonCheckData(String realName) {
		return mainDao.getWlTkNonCheckData(realName);
	}

	public List<BcpRkdBean> getBcpRkNonCheckData(String realName) {
		return mainDao.getBcpRkNonCheckData(realName);
	}

	public List<BcpCkdBean> getBcpCkNonCheckData(String realName) {
		return mainDao.getBcpCkNonCheckData(realName);
	}

	public List<BcpTkdBean> getBcpTkNonCheckData(String realName) {
		return mainDao.getBcpTkNonCheckData(realName);
	}

	public WlRkdBean getWlRkdBean(String dh) {
		return mainDao.getWlRkdBean(dh);
	}

	public List<WLINShowBean> getWLINShowBeanListByInDh(String dh) {
		List<WLINShowBean> list=mainDao.getWLINShowBeanListByInDh(dh);
		return list;
	}

	public WlCkdBean getWlCkdBean(String dh) {
		return mainDao.getWlCkdBean(dh);
	}

	public List<WLOutShowBean> getWLOutShowBeanListByInDh(String dh) {
		List<WLOutShowBean> list=mainDao.getWLOutShowBeanListByInDh(dh);
		return list;
	}

	public WlTkdBean getWlTkdBean(String dh) {
		return mainDao.getWlTkdBean(dh);
	}

	public List<WLTkShowBean> getWLTkShowBeanListByInDh(String dh) {
		List<WLTkShowBean> list=mainDao.getWLTkShowBeanListByInDh(dh);
		return list;
	}

	public BcpRkdBean getBcpRkdBean(String dh) {
		return mainDao.getBcpRkdBean(dh);
	}

	public List<BcpInShowBean> getBcpInShowBeanListByInDh(String dh) {
		List<BcpInShowBean> list=mainDao.getBcpInShowBeanListByInDh(dh);
		return list;
	}

	public List<BcpInShowBean> getCpInShowBeanListByInDh(String dh) {
		List<BcpInShowBean> list=mainDao.getCpInShowBeanListByInDh(dh);
		return list;
	}

	public List<BcpInShowBean> getBigCpInShowBeanListByInDh(String dh) {
		List<BcpInShowBean> list=mainDao.getBigCpInShowBeanListByInDh(dh);
		return list;
	}

	public BcpCkdBean getBcpCkdBean(String dh) {
		return mainDao.getBcpCkdBean(dh);
	}

	public List<CpOutShowBean> getCpOutShowBeanListByOutDh(String dh) {
		List<CpOutShowBean> list=mainDao.getCpOutShowBeanListByOutDh(dh);
		return list;
	}

	public BcpTkdBean getBcpTkdBean(String dh) {
		return mainDao.getBcpTkdBean(dh);
	}

	public List<BcpTkShowBean> getBcpTkShowBeanListByBackDh(String dh) {
		List<BcpTkShowBean> list=mainDao.getBcpTkShowBeanListByBackDh(dh);
		return list;
	}

	public int agreeWlIn(String dh) {
    	return mainDao.agreeWlIn(dh);
	}

	public int refuseWlIn(String dh) {
		return mainDao.refuseWlIn(dh);
	}

	public int agreeWlOut(String dh) {
		return mainDao.agreeWlOut(dh);
	}

	public int refuseWlOut(String dh) {
		return mainDao.refuseWlOut(dh);
	}

	public int agreeWlTk(String dh) {
		return mainDao.agreeWlTk(dh);
	}

	public int refuseWlTk(String dh) {
		return mainDao.refuseWlTk(dh);
	}

	public int agreeBcpIn(String dh) {
		return mainDao.agreeBcpIn(dh);
	}

	public int refuseBcpIn(String dh) {
		return mainDao.refuseBcpIn(dh);
	}

	public int agreeBcpOut(String dh) {
		return mainDao.agreeBcpOut(dh);
	}

	public int refuseBcpOut(String dh) {
		return mainDao.refuseBcpOut(dh);
	}

	public int agreeBcpTk(String dh) {
		return mainDao.agreeBcpTk(dh);
	}

	public int refuseBcpTk(String dh) {
		return mainDao.refuseBcpTk(dh);
	}

	public List<BcpTrackResult> getBcpInShowData(String qrCodeId) {
		return mainDao.getBcpInShowData(qrCodeId);
	}

	public List<SmallCpTrackResult> getSmallCpInShowData(String qrCodeId) {
		return mainDao.getSmallCpInShowData(qrCodeId);
	}

	public List<BigCpTrackResult> getBigCpIn2ShowData(String qrCodeId) {
		return mainDao.getBigCpIn2ShowData(qrCodeId);
	}

	public int changeWLInPassCheckFlag(String qrCodeId, String zjy) {
		return mainDao.changeWLInPassCheckFlag(qrCodeId,zjy);
	}

	public int changeBCPInPassCheckFlag(String qrCodeId, String zjy) {
		return mainDao.changeBCPInPassCheckFlag(qrCodeId,zjy);
	}

	public int changeSMALL_CPInPassCheckFlag(String qrCodeId, String zjy) {
		return mainDao.changeSMALL_CPInPassCheckFlag(qrCodeId,zjy);
	}

	public int changeBIG_CPInPassCheckFlag(String qrCodeId, String zjy) {
		return mainDao.changeBIG_CPInPassCheckFlag(qrCodeId,zjy);
	}

	public List<WlRkdBean> getWlRkNonPassCheckDataByCzy(String realName) {
		return mainDao.getWlRkNonPassCheckDataByCzy(realName);
	}

	public List<WlCkdBean> getWlCkNonPassCheckDataByCzy(String realName) {
		return mainDao.getWlCkNonPassCheckDataByCzy(realName);
	}

	public List<WlTkdBean> getWlTkNonPassCheckDataByCzy(String realName) {
		return mainDao.getWlTkNonPassCheckDataByCzy(realName);
	}

	public List<BcpRkdBean> getBcpRkNonPassCheckDataByCzy(String realName) {
		return mainDao.getBcpRkNonPassCheckDataByCzy(realName);
	}

	public List<BcpCkdBean> getBcpCkNonPassCheckDataByCzy(String realName) {
		return mainDao.getBcpCkNonPassCheckDataByCzy(realName);
	}

	public List<BcpTkdBean> getBcpTkNonPassCheckDataByCzy(String realName) {
		return mainDao.getBcpTkNonPassCheckDataByCzy(realName);
	}

	public int updateWlInData(WLINShowBean param) {
		return mainDao.updateWlInData(param);
	}

	public int updateWlRkdData(WlInVerifyResult param) {
		return mainDao.updateWlRkdData(param);
	}

	public int updateWlCkdData(WlOutVerifyResult param) {
		return mainDao.updateWlCkdData(param);
	}

	public int updateWlOutData(WLOutShowBean wlOutShowBean) {
		return mainDao.updateWlOutData(wlOutShowBean);
	}

	public int updateWlTkdData(WlTkVerifyResult param) {
		return mainDao.updateWlTkdData(param);
	}

	public int updateWlTkData(WLTkShowBean wlTkShowBean) {
		return mainDao.updateWlTkData(wlTkShowBean);
	}

	public int updateBcpRkdData(BcpInVerifyResult param) {
		return mainDao.updateBcpRkdData(param);
	}

	public int updateBcpInData(BcpInShowBean bcpInShowBean) {
		return mainDao.updateBcpInData(bcpInShowBean);
	}

	public int updateSmallCpInData(BcpInShowBean bcpInShowBean) {
		return mainDao.updateSmallCpInData(bcpInShowBean);
	}

	public int updateBigCpInData(BcpInShowBean bcpInShowBean) {
		return mainDao.updateBigCpInData(bcpInShowBean);
	}

	public int updateBcpTkdData(BcpTkVerifyResult param) {
		return mainDao.updateBcpTkdData(param);
	}

	public int updateBcpTkData(BcpTkShowBean bcpTkShowBean) {
		return mainDao.updateBcpTkData(bcpTkShowBean);
	}

	public int updateCpCkdData(CpOutVerifyResult param) {
		return mainDao.updateCpCkdData(param);
	}

	public int updateWLIN_M(WLINParam wlinParam) {
		return mainDao.updateWLIN_M(wlinParam);
	}

	public int createWL_RKD_New(CreateWLRKDParam rkdpParams) {
		return mainDao.createWL_RKD_New(rkdpParams);
	}

	public PersonResult getAllPerson() {
		List<PersonBean> beans =  mainDao.getAllPerson();
		PersonResult personResult = new PersonResult();
		personResult.setPersonBeans(beans);
		return personResult;
	}

	public int updateBcpIn(BCPINParam bcpInParam) {
		return mainDao.updateBcpIn(bcpInParam);
	}

	public List<WlRkdBean> getWlRkCanModifyData(String realName) {
		return mainDao.getWlRkCanModifyData(realName);
	}

	public List<WlCkdBean> getWlCkCanModifyData(String realName) {
		return mainDao.getWlCkCanModifyData(realName);
	}

	public List<WlTkdBean> getWlTkCanModifyData(String realName) {
		return mainDao.getWlTkCanModifyData(realName);
	}

	public List<BcpRkdBean> getBcpRkCanModifyData(String realName) {
		return mainDao.getBcpRkCanModifyData(realName);
	}

	public List<BcpCkdBean> getBcpCkCanModifyData(String realName) {
		return mainDao.getBcpCkCanModifyData(realName);
	}

	public List<BcpTkdBean> getBcpTkCanModifyData(String realName) {
		return mainDao.getBcpTkCanModifyData(realName);
	}

	public int updateCPIn(SmallCPINParam inParam) {
		return mainDao.updateCPIn(inParam);
	}

	public int updateCPIn2ByParam(BigCPINParam inParam) {
		return mainDao.updateCPIn2ByParam(inParam);
	}
}
