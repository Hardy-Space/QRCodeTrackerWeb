package com.qdhualing.qrcodetracker.controll;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.service.MainService;
import com.qdhualing.qrcodetracker.utils.ActionResultUtils;
import com.qdhualing.qrcodetracker.utils.ParamsUtils;
import com.qdhualing.qrcodetracker.utils.RandomUtil;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.managed.ManagedTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
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
    @RequestMapping(value = "/createWL_RKD", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createWL_RKD(String json) {
        CreateWLRKDParam rkdpParams = ParamsUtils.handleParams(json, CreateWLRKDParam.class);
        ActionResult<WLRKDResult> result = new ActionResult<WLRKDResult>();
        if (rkdpParams != null) {
            Date data = new Date();
            long indhL = data.getTime() ;
            String indh = String.valueOf(indhL) + RandomUtil.getRandomLong();
            rkdpParams.setInDh(indh);
            try {
                int c = mainService.getCreateRKDParamByInDh(rkdpParams.getInDh());
                if (c == 0) {
                    int a = mainService.createWL_RKD(rkdpParams);
                    int b = mainService.createWLWT_RKD(rkdpParams);
                    if (a <= 0 || b <= 0) {
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "入库单不正确");
                    } else if (a == 1 && b == 1) {
                        WLRKDResult rdk = new WLRKDResult();
                        rdk.setInDh(indh);
                        result.setResult(rdk);
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "入库单保存成功");
                    }
                }
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单已存在");
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单不正确");
    }

    //扫码录入
    @RequestMapping(value = "/createWLIn_M", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createWLIn_M(String json) {
        WLINParam wlinParam = ParamsUtils.handleParams(json, WLINParam.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        wlinParam.setlLTime(df.format(System.currentTimeMillis()));
        ActionResult<DataResult> result = new ActionResult<DataResult>();
        if (wlinParam != null && wlinParam.getInDh() != null) {
            try {
                int a = mainService.createWLIN_M(wlinParam);
                if (a <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
                } else {
                    /**
                     * @author 马鹏昊
                     * @desc 插入到库存表
                     */
                    a = mainService.queryWLS(wlinParam.getqRCodeID());
                    if (a<=0){
                        a =  mainService.insertWLS(wlinParam);
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "库存表插入记录成功");
                    }else if(a>1){
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "库存表中出现了二维码id相同的多条记录");
                    }else{
                        a =  mainService.updateWLS(wlinParam);
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "库存表修改记录成功");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库数据不正确");
    }

    @RequestMapping(value = "/delWLIN_M", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult delWLWTAndWLINAndWLAndWLS(String json) {
        WLINParam wlinParam = ParamsUtils.handleParams(json, WLINParam.class);
        ActionResult<DataResult> result = new ActionResult<DataResult>();
        if (wlinParam != null && wlinParam.getInDh() != null) {
            try {
                List<WLINParam> list = mainService.getWLINParamListByInDh(wlinParam.getInDh());
                if (list.size() <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库单号不正确");
                }
                for (WLINParam even : list) {
                    WLINParam wls = mainService.getWLSParamByQRCode(even.getqRCodeID());
                    wls.setpCZL(wls.getpCZL() - even.getpCZL());
                    mainService.updataWLINParamByQRCode(wls.getpCZL(), wls.getqRCodeID());
                }
            } catch (Exception e) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "入库数据不正确");
    }

    /**
     * @author 马鹏昊
     * @desc 获取物料二级分类（即类别）
     * @return
     */
    @RequestMapping(value = "/getPdtSort", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getPdtSort() {
        ActionResult<PdtSortResult> result = new ActionResult<PdtSortResult>();
        try {
            PdtSortResult pdtSortResult = mainService.getPdtSort();
            if (pdtSortResult.getSortBeans() == null || pdtSortResult.getSortBeans().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无分类数据");
            }
            result.setResult(pdtSortResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取类别成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }


    /**
     * @author 马鹏昊
     * @desc 获取物料分类（含物料编码）
     * @return
     */
    @RequestMapping(value = "/getHlSort", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getHlSort() {
        ActionResult<HlSortResult> result = new ActionResult<HlSortResult>();
        try {
            HlSortResult hlSortResult = mainService.getHlSort();
            if (hlSortResult.getHlSortBeans() == null || hlSortResult.getHlSortBeans().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无物料类别数据");
            }
            result.setResult(hlSortResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取类别成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @author 马鹏昊
     * @desc 生成物料出库单
     * @return
     */
    @RequestMapping(value = "/createWL_CKD", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createWL_CKD(String json) {
        CreateWLCKDParam ckdParam = ParamsUtils.handleParams(json, CreateWLCKDParam.class);
        ActionResult<WLCKDResult> result = new ActionResult<WLCKDResult>();
        if (ckdParam != null) {
            Date data = new Date();
            long time = data.getTime() ;
            String outDh = String.valueOf(time) + RandomUtil.getRandomLong();
            ckdParam.setOutDh(outDh);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ckdParam.setLhRq(sdf.format(data));
            try {
                int a = mainService.createWL_CKD(ckdParam);
                int b = mainService.createWLWT_CKD(ckdParam);
                if (a <= 0 || b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出库单不正确");
                } else {
                    WLCKDResult ckd = new WLCKDResult();
                    ckd.setOutdh(outDh);
                    result.setResult(ckd);
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "出库单创建成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "出库单不正确");
    }

    /**
     * @author 马鹏昊
     * @desc 物料出库界面显示数据获取
     * @return
     */
    @RequestMapping(value = "/getWlOutShowData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getWlOutShowData(String json) {
        WLOutGetShowDataParam param = ParamsUtils.handleParams(json, WLOutGetShowDataParam.class);
        ActionResult<WLOutShowDataResult> result = new ActionResult<WLOutShowDataResult>();
        if (param != null) {
            try {
                WLOutShowDataResult showDataResult = mainService.getWLSData(param.getQrcodeId());
                if (showDataResult==null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "获取基本信息失败,请重新扫码");
                } else {
                    result.setResult(showDataResult);
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "获取基本信息失败,请重新扫码");
    }
    /**
     * @author 马鹏昊
     * @desc 物料出库
     * @return
     */
    @RequestMapping(value = "/wlOut", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult wlOut(String json) {
        WLOutParam wlOutParam = ParamsUtils.handleParams(json, WLOutParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        if (wlOutParam != null) {
            Date data = new Date();
            long time = data.getTime() ;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            wlOutParam.setTime(sdf.format(data));
            try {
                CKDWLBean ckdwlBean = mainService.findWL_CKD(wlOutParam.getOutDh());
                if (ckdwlBean==null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出库单不存在");
                }
                wlOutParam.setFlr(ckdwlBean.getFhR());
                wlOutParam.setLlr(ckdwlBean.getLhR());
                WLSBean wlsBean = mainService.findWLS(wlOutParam.getQrCodeId());
                if (wlsBean==null)
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该物料，请先入库");
                wlOutParam.setProductName(wlsBean.getProductName());
                wlOutParam.setWlCode(wlsBean.getWLCode());
                wlOutParam.setDw(wlsBean.getDW());
                wlOutParam.setDwzl(wlsBean.getDWZL());
                wlOutParam.setGg(wlsBean.getGG());
                wlOutParam.setSortId(wlsBean.getSortID());
                wlOutParam.setYlpc(wlsBean.getYLPC());
                int b = mainService.insertWLOUT(wlOutParam);
                if ( b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "生成出库记录失败");
                } else {
                    b = mainService.outUpdateWLS(wlOutParam);
                    if (b<=0) {
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "修改库存表数据失败");
                    }
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "失败");
    }


    /**
     * @author 马鹏昊
     * @desc 获取部门数据
     * @return
     */
    @RequestMapping(value = "/getDepartmentData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getDepartmentData() {
        ActionResult<UserGroupResult> result = new ActionResult<UserGroupResult>();
        try {
            UserGroupResult userGroupResult = mainService.getUserGroupData();
            if (userGroupResult.getGroupBeanList() == null || userGroupResult.getGroupBeanList().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无部门数据");
            }
            result.setResult(userGroupResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取部门数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }


//    
//    @RequestMapping(value = "/saveRKD",method=RequestMethod.POST)
//    @ResponseBody
//    public ActionResult saveRKD(String json,CreateWLRKDParam rkdpParams) {
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
