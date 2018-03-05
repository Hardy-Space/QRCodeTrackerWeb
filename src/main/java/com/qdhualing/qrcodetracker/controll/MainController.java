package com.qdhualing.qrcodetracker.controll;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.service.MainService;
import com.qdhualing.qrcodetracker.utils.ActionResultUtils;
import com.qdhualing.qrcodetracker.utils.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2018/1/26.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService mainService;
    
    //创建入库单表头信息
    @RequestMapping(value = "/createWL_RKD",method=RequestMethod.POST)
    @ResponseBody
    public ActionResult createWL_RKD(String json) {    	CreateRKDParam rkdpParams=ParamsUtils.handleParams(json, CreateRKDParam.class);
    	ActionResult<RKDResult> result = new ActionResult<RKDResult>();
    	if(rkdpParams!=null) {
    		Date data=new Date();
    		long indh=data.getTime();
    		RKDResult rdk=new RKDResult();
    		rkdpParams.setInDh(indh);
    		rdk.setIndh(indh);
    		result.setResult(rdk);
    		try {
    			int c=mainService.getCreateRKDParamByInDh(rkdpParams.getInDh());
    			if(c==0) {
    				int a =mainService.createWL_RKD(rkdpParams);
        			int b=mainService.createWLWT_RKD(rkdpParams);
                	 if (a<=0||b<=0) {
                		 return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
                     } else if(a==1&&b==1){
                    	 return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "入库单保存成功");
                     }
    			}
    			 return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单已存在");
			} catch (Exception e) {
				e.printStackTrace();
				return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
			}
    	}
    	return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单不正确");
    }
    
    //扫码录入
    @RequestMapping(value = "/createWLIn_M",method=RequestMethod.POST)
    @ResponseBody
    public ActionResult createWLIn_M(String json) {
    	WLINParam wlinParam=ParamsUtils.handleParams(json, WLINParam.class);
    	ActionResult<DataResult> result = new ActionResult<DataResult>();
    	if(wlinParam!=null&&wlinParam.getInDh()!=null) {
    		try {
    				int a=mainService.createWLIN_M(wlinParam);
        			if(a<=0) {
        				return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
        			}else {
        				 return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "入库物料保存成功");
        			}
    		}catch (Exception e) {
    			e.printStackTrace();
    			return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
			}
    	}
    	return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库数据不正确");
    }
    @RequestMapping(value = "/delWLIN_M",method=RequestMethod.POST)
    @ResponseBody
    public ActionResult delWLWTAndWLINAndWLAndWLS(String json) {
    	WLINParam wlinParam=ParamsUtils.handleParams(json, WLINParam.class);
    	ActionResult<DataResult> result = new ActionResult<DataResult>();
    	if(wlinParam!=null&&wlinParam.getInDh()!=null) {
    		try {
    			List<WLINParam> list=mainService.getWLINParamListByInDh(wlinParam.getInDh());
    			if(list.size()<=0) {
    				return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单号不正确");
    			}
    			for(WLINParam even:list) {
    				WLINParam wls=mainService.getWLSParamByQRCode(even.getqRCodeID());
    				wls.setpCZL(wls.getpCZL()-even.getpCZL());
    				mainService.updataWLINParamByQRCode(wls.getpCZL(), wls.getqRCodeID());
    			}
    		}catch (Exception e) {
    			return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
			}
    	}
    	return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库数据不正确");
    }
    
    
    
    
//    
//    @RequestMapping(value = "/saveRKD",method=RequestMethod.POST)
//    @ResponseBody
//    public ActionResult saveRKD(String json,CreateRKDParam rkdpParams) {
//    	ActionResult<DataResult> result = new ActionResult<DataResult>();
//    	if(json!=null) {
//    		try {
//    			GetNeedInputedDataParams params=ParamsUtils.handleParams(json, GetNeedInputedDataParams.class);
//            	DataResult data = mainService.getMaterialInInputedData(params);
//            	 if (data == null) {
//            		 return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
//                 } else {
//                	 return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "入库单保存成功");
//                 }
//			} catch (Exception e) {
//				return  ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
//			}
//    	}
//    	return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单不正确");
//    }
//    
//    @RequestMapping(value = "/getInputedData", method = RequestMethod.POST)
//    @ResponseBody
//    public ActionResult getInputedData(String json) {
//        GetNeedInputedDataParams params = ParamsUtils.handleParams(json, GetNeedInputedDataParams.class);
//        int type = params.getType();
//        switch (type) {
//            case FunctionType.MATERIAL_IN:
//                DataResult data = mainService.getMaterialInInputedData(params);
//                ActionResult<DataResult> result = new ActionResult<DataResult>();
//                if (data == null) {
//                    result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
//                } else {
//                    result.setResult(data);
//                }
//                return result;
//            case FunctionType.MATERIAL_OUT:
//
//                break;
//            case FunctionType.HALF_PRODUCT_IN:
//
//                break;
//            case FunctionType.PRODUCT_IN:
//
//                break;
//            case FunctionType.PRODUCT_OUT:
//
//                break;
//            case FunctionType.MATERIAL_THROW:
//
//                break;
//            case FunctionType.MATERIAL_RETURN:
//
//                break;
//        }
//        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
//        result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
//        return result;
//    }
//
//
//    @RequestMapping(value = "/commitInputedData", method = RequestMethod.POST)
//    @ResponseBody
//    public ActionResult commitInputedData(String json) {
//        DataInputParams params = ParamsUtils.handleListParams(json, DataInputParams.class,"needToInputDataList",DataBean.class);
//        int type = params.getType();
//        switch (type) {
//            case FunctionType.MATERIAL_IN:
//                int row = mainService.commitMaterialInputedData(params);
//                ActionResult<DataResult> result = new ActionResult<DataResult>();
//                if (row < 0) {
//                    result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "提交失败");
//                } else {
//                    result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "提交成功");
//                }
//                return result;
//            case FunctionType.MATERIAL_OUT:
//
//                break;
//            case FunctionType.HALF_PRODUCT_IN:
//
//                break;
//            case FunctionType.PRODUCT_IN:
//
//                break;
//            case FunctionType.PRODUCT_OUT:
//
//                break;
//            case FunctionType.MATERIAL_THROW:
//
//                break;
//            case FunctionType.MATERIAL_RETURN:
//
//                break;
//        }
//        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
//        return result;
//    }

}
