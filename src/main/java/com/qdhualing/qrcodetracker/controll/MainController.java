package com.qdhualing.qrcodetracker.controll;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.service.MainService;
import com.qdhualing.qrcodetracker.utils.*;
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
            long indhL = data.getTime();
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
                    if (a <= 0) {
                        a = mainService.insertWLS(wlinParam);
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "库存表插入记录成功");
                    } else if (a > 1) {
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "库存表中出现了二维码id相同的多条记录");
                    } else {
                        a = mainService.updateWLS(wlinParam);
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
     * @return
     * @author 马鹏昊
     * @desc 获取物料二级分类（即类别）
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
     * @return
     * @author 马鹏昊
     * @desc 获取物料分类（含物料编码）
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
     * @return
     * @author 马鹏昊
     * @desc 生成物料出库单
     */
    @RequestMapping(value = "/createWL_CKD", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createWL_CKD(String json) {
        CreateWLCKDParam ckdParam = ParamsUtils.handleParams(json, CreateWLCKDParam.class);
        ActionResult<WLCKDResult> result = new ActionResult<WLCKDResult>();
        if (ckdParam != null) {
            Date data = new Date();
            long time = data.getTime();
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
     * @return
     * @author 马鹏昊
     * @desc 物料出库界面显示数据获取
     */
    @RequestMapping(value = "/getWlOutShowData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getWlOutShowData(String json) {
        WLOutGetShowDataParam param = ParamsUtils.handleParams(json, WLOutGetShowDataParam.class);
        ActionResult<WLOutShowDataResult> result = new ActionResult<WLOutShowDataResult>();
        if (param != null) {
            try {
                WLOutShowDataResult showDataResult = mainService.getWLSData(param.getQrcodeId());
                if (showDataResult == null) {
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
     * @return
     * @author 马鹏昊
     * @desc 物料出库
     */
    @RequestMapping(value = "/wlOut", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult wlOut(String json) {
        WLOutParam wlOutParam = ParamsUtils.handleParams(json, WLOutParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        if (wlOutParam != null) {
            Date data = new Date();
            long time = data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            wlOutParam.setTime(sdf.format(data));
            try {
                CKDWLBean ckdwlBean = mainService.findWL_CKD(wlOutParam.getOutDh());
                if (ckdwlBean == null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出库单不存在");
                }
                wlOutParam.setFlr(ckdwlBean.getFhR());
                wlOutParam.setLlr(ckdwlBean.getLhR());
                wlOutParam.setLlbm(ckdwlBean.getLhDw());
                WLSBean wlsBean = mainService.findWLS(wlOutParam.getQrCodeId());
                if (wlsBean == null)
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该物料，请先入库");
                wlOutParam.setProductName(wlsBean.getProductName());
                wlOutParam.setWlCode(wlsBean.getWLCode());
                wlOutParam.setDw(wlsBean.getDW());
                wlOutParam.setDwzl(wlsBean.getDWZL());
                wlOutParam.setGg(wlsBean.getGG());
                wlOutParam.setSortId(wlsBean.getSortID());
                wlOutParam.setYlpc(wlsBean.getYLPC());
                wlOutParam.setChd(wlsBean.getCHD());
                int b = mainService.insertWLOUT(wlOutParam);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "生成出库记录失败");
                } else {
                    //更新仓库库存表数量
                    b = mainService.outUpdateWLS(wlOutParam);
                    if (b <= 0) {
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "修改库存表数据失败");
                    }
                    //查询临时库存表中是否有数据
                    b = mainService.findWLTempS(wlOutParam.getQrCodeId());
                    if (b <= 0) {
                        //插入临时库存表（车间）
                        b = mainService.insertWLTempS(wlOutParam);
                    } else {
                        b = mainService.updateWLTempS(wlOutParam);
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
     * @return
     * @author 马鹏昊
     * @desc 获取部门数据
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

    /**
     * @return
     * @author 马鹏昊
     * @desc 生成物料退库单
     */
    @RequestMapping(value = "/createWL_TKD", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createWL_TKD(String json) {
        CreateWLTKDParam tkdParam = ParamsUtils.handleParams(json, CreateWLTKDParam.class);
        ActionResult<WLTKDResult> result = new ActionResult<WLTKDResult>();
        if (tkdParam != null) {
            Date data = new Date();
            long time = data.getTime();
            String backDh = String.valueOf(time) + RandomUtil.getRandomLong();
            tkdParam.setBackDh(backDh);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tkdParam.setThRq(sdf.format(data));
            try {
                int a = mainService.createWL_TKD(tkdParam);
                if (a <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出错");
                } else {
                    WLTKDResult tkd = new WLTKDResult();
                    tkd.setBackDh(backDh);
                    result.setResult(tkd);
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "退库单创建成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "出错");
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 物料退库界面显示数据获取
     */
    @RequestMapping(value = "/getWlTKShowData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getWlTKShowData(String json) {
        WLTKGetShowDataParam param = ParamsUtils.handleParams(json, WLTKGetShowDataParam.class);
        ActionResult<WLTKShowDataResult> result = new ActionResult<WLTKShowDataResult>();
        if (param != null) {
            try {
                WLTKShowDataResult showDataResult = mainService.getWLTempSData(param.getQrcodeId());
                if (showDataResult == null) {
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
     * @return
     * @author 马鹏昊
     * @desc 物料退库
     */
    @RequestMapping(value = "/wlTk", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult wlTK(String json) {
        WLTKParam wlTKParam = ParamsUtils.handleParams(json, WLTKParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        if (wlTKParam != null) {
            Date data = new Date();
            long time = data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            wlTKParam.setTime(sdf.format(data));
            try {
                //首先查找退库单信息
                TKDWLBean tkdwlBean = mainService.findWL_TKD(wlTKParam.getOutDh());
                if (tkdwlBean == null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "退库单不存在");
                }
                wlTKParam.setFlr(tkdwlBean.getThR());
                wlTKParam.setLlr(tkdwlBean.getShR());
                wlTKParam.setTkbm(tkdwlBean.getThDw());
                //查找临时库存表信息
                WLTempSBean wlTempSBean = mainService.getWLTempS(wlTKParam.getQrCodeId());
                if (wlTempSBean == null)
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该物料，请先出库");
                wlTKParam.setProductName(wlTempSBean.getProductName());
                wlTKParam.setWlCode(wlTempSBean.getWLCode());
                wlTKParam.setDw(wlTempSBean.getDW());
                wlTKParam.setDwzl(wlTempSBean.getDWZL());
                wlTKParam.setGg(wlTempSBean.getGG());
                wlTKParam.setSortId(wlTempSBean.getSortID());
                wlTKParam.setYlpc(wlTempSBean.getYLPC());
                wlTKParam.setChd(wlTempSBean.getCHD());
                //生成退库记录
                int b = mainService.insertWLBk(wlTKParam);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "生成退库记录失败");
                } else {
                    //更新仓库库存表数量（退库的数量加上）
                    b = mainService.updateWLSByTk(wlTKParam);
                    if (b <= 0) {
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "修改库存表数据失败");
                    }
                    //临时库存表中数据减去或者删除
                    if (wlTKParam.getTkShL() >= wlTempSBean.getSHL()) {
                        b = mainService.deleteFromWLTempS(wlTKParam.getQrCodeId());
                    } else {
                        b = mainService.updateWLTempSByTk(wlTKParam);
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
     * @return
     * @author 马鹏昊
     * @desc 获取物料投料显示数据
     */
    @RequestMapping(value = "/getWlTLShowData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getWlTLShowData(String json) {
        WLThrowGetShowDataParam param = ParamsUtils.handleParams(json, WLThrowGetShowDataParam.class);
        ActionResult<WLThrowShowDataResult> result = new ActionResult<WLThrowShowDataResult>();
        if (param != null) {
            try {
                WLTKShowDataResult showDataResult = mainService.getWLTempSData(param.getQrcodeId());
                if (showDataResult == null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "获取基本信息失败,请重新扫码");
                } else {
                    WLThrowShowDataResult wlThrowShowDataResult = new WLThrowShowDataResult();
                    wlThrowShowDataResult.setProductName(showDataResult.getProductName());
                    wlThrowShowDataResult.setChd(showDataResult.getChd());
                    wlThrowShowDataResult.setDw(showDataResult.getDw());
                    wlThrowShowDataResult.setDwzl(showDataResult.getDwzl());
                    wlThrowShowDataResult.setGg(showDataResult.getGg());
                    wlThrowShowDataResult.setShl(showDataResult.getShl());
                    wlThrowShowDataResult.setSortName(showDataResult.getSortName());
                    result.setResult(wlThrowShowDataResult);
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
     * @return
     * @author 马鹏昊
     * @desc 物料投料操作
     */
    @RequestMapping(value = "/wlThrow", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult wlThrow(String json) {
        WLThrowParam wlTLParam = ParamsUtils.handleParams(json, WLThrowParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        if (wlTLParam != null) {
            Date data = new Date();
            long time = data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            wlTLParam.setTlTime(sdf.format(data));
            try {
                //查找临时库存表信息
                WLTempSBean wlTempSBean = mainService.getWLTempS(wlTLParam.getQrcodeId());
                if (wlTempSBean == null)
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该物料，请先出库");
                wlTLParam.setProductName(wlTempSBean.getProductName());
                wlTLParam.setWlCode(wlTempSBean.getWLCode());
                wlTLParam.setDw(wlTempSBean.getDW());
                wlTLParam.setDwzl(wlTempSBean.getDWZL());
                wlTLParam.setGg(wlTempSBean.getGG());
                wlTLParam.setSortId(wlTempSBean.getSortID());
                wlTLParam.setYlpc(wlTempSBean.getYLPC());
                int b = mainService.getWLTLDataCount(wlTLParam.getQrcodeId());
                if (b <= 0) {
                    //生成物料投料记录
                    b = mainService.insertWLTl(wlTLParam);
                } else if (b > 1) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "物料投料表数据不唯一");
                } else {
                    //更新物料投料记录
                    b = mainService.updateWLTl(wlTLParam);
                }
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "生成物料投料记录失败");
                } else {
                    //临时库存表中数据减去或者删除
                    if (wlTLParam.getTlShl() >= wlTempSBean.getSHL()) {
                        b = mainService.deleteFromWLTempS(wlTLParam.getQrcodeId());
                    } else {
                        b = mainService.updateWLTempSByTl(wlTLParam);
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
     * @return
     * @author 马鹏昊
     * @desc 获取车间数据
     */
    @RequestMapping(value = "/getCJ", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getCJData() {
        ActionResult<CJResult> result = new ActionResult<CJResult>();
        try {
            CJResult cjResult = mainService.getCJData();
            if (cjResult.getCjBeans() == null || cjResult.getCjBeans().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无车间数据");
            }
            result.setResult(cjResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取车间数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取工序数据
     */
    @RequestMapping(value = "/getGX", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getGXData(String json) {
        GetGXParam gxParam = ParamsUtils.handleParams(json, GetGXParam.class);
        String cjGXIds = gxParam.getCjGXIds();
        String[] cjIdArray = cjGXIds.split(",");
        ActionResult<GXResult> result = new ActionResult<GXResult>();
        try {
            GXResult gxResult = mainService.getGXData(cjIdArray);
            if (gxResult.getGxBeans() == null || gxResult.getGxBeans().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "该车间无工序数据");
            }
            result.setResult(gxResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取工序数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 生成半成品/成品入库单
     */
    @RequestMapping(value = "/createBcpRkd", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createBcpRkd(String json) {
        CreateBCPRKDParam rkdParam = ParamsUtils.handleParams(json, CreateBCPRKDParam.class);
        ActionResult<BCPRKDResult> result = new ActionResult<BCPRKDResult>();
        if (rkdParam != null) {
            Date data = new Date();
            long time = data.getTime();
            String inDh = String.valueOf(time) + RandomUtil.getRandomLong();
            rkdParam.setInDh(inDh);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rkdParam.setShRq(sdf.format(data));
            try {
                int a = mainService.createBCP_RKD(rkdParam);
                if (a <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出错");
                } else {
                    BCPRKDResult rkd = new BCPRKDResult();
                    rkd.setIndh(inDh);
                    result.setResult(rkd);
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "半成品入库单创建成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "出错");
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取所需原料（物料和半成品投料表）
     */
    @RequestMapping(value = "/getTLYL", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getTLYL(String json) {
        GetSXYLParam gxParam = ParamsUtils.handleParams(json, GetSXYLParam.class);
        int gxId = gxParam.getGxId();
        ActionResult<SXYLResult> result = new ActionResult<SXYLResult>();
        try {
            SXYLResult sxylResult = mainService.getSXYLData(gxId);
            if (sxylResult.getTlylList() == null || sxylResult.getTlylList().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无数据,请先进行投料");
            }
            result.setResult(sxylResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取工序数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 半成品入库（临时库存，即车间）
     */
    @RequestMapping(value = "/bcpIn", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult bcpIn(String json) {
        BCPINParam bcpInParam = ParamsUtils.handleParams(json, BCPINParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        try {
            int b = mainService.insertBCPIn(bcpInParam);
            if (b <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "插入BCPIn失败");
            }
            //查询临时库存表中是否有数据
            b = mainService.findBCPTempS(bcpInParam.getQrCodeId());
            if (b <= 0) {
                //插入临时库存表（车间）
                b = mainService.insertBCPTempS(bcpInParam);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "插入BCPTempS失败");
                }
            } else {
                b = mainService.updateBCPTempS(bcpInParam);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "更新BCPTempS失败");
                }
            }
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "半成品入库成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取半成品投料显示数据
     */
    @RequestMapping(value = "/getBcpTLShowData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getBcpTLShowData(String json) {
        BcpThrowGetShowDataParam param = ParamsUtils.handleParams(json, BcpThrowGetShowDataParam.class);
        ActionResult<BcpThrowShowDataResult> result = new ActionResult<BcpThrowShowDataResult>();
        if (param != null) {
            try {
                BcpThrowShowDataResult showDataResult = mainService.getBcpTempSData(param.getQrcodeId());
                if (showDataResult == null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该半成品,请先入库");
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
     * @return
     * @author 马鹏昊
     * @desc 半成品投料操作
     */
    @RequestMapping(value = "/bcpThrow", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult bcpThrow(String json) {
        BcpThrowParam bcpTLParam = ParamsUtils.handleParams(json, BcpThrowParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        if (bcpTLParam != null) {
            Date data = new Date();
            long time = data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bcpTLParam.setTlTime(sdf.format(data));
            try {
                //查找临时库存表信息
                BCPTempSBean bcpTempSBean = mainService.getBcpTempS(bcpTLParam.getQrcodeId());
                if (bcpTempSBean == null)
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该半成品，请先入库");
                bcpTLParam.setProductName(bcpTempSBean.getProductName());
                bcpTLParam.setBcpCode(bcpTempSBean.getBcpCode());
                bcpTLParam.setDw(bcpTempSBean.getDw());
                bcpTLParam.setDwzl(bcpTempSBean.getDwzl());
                bcpTLParam.setGg(bcpTempSBean.getGg());
                bcpTLParam.setSortId(bcpTempSBean.getSortID());
                bcpTLParam.setYlpc(bcpTempSBean.getYlpc());
                int b = mainService.getBcpTLDataCount(bcpTLParam.getQrcodeId());
                if (b <= 0) {
                    //生成半成品投料记录
                    b = mainService.insertBcpTl(bcpTLParam);
                } else if (b > 1) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "半成品投料表数据不唯一");
                } else {
                    //更新半成品投料记录
                    b = mainService.updateBcpTl(bcpTLParam);
                }
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "生成半成品投料记录失败");
                } else {
                    //临时库存表中数据减去或者删除
                    if (bcpTLParam.getTlShl() >= bcpTempSBean.getShl()) {
                        b = mainService.deleteFromBcpTempS(bcpTLParam.getQrcodeId());
                    } else {
                        b = mainService.updateBcpTempSByTl(bcpTLParam);
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
     * @return
     * @author 马鹏昊
     * @desc 生成半成品退库单
     */
    @RequestMapping(value = "/createBCP_TKD", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createBCP_TKD(String json) {
        CreateBCPTKDParam param = ParamsUtils.handleParams(json, CreateBCPTKDParam.class);
        ActionResult<BCPTKDResult> result = new ActionResult<BCPTKDResult>();
        if (param != null) {
            Date data = new Date();
            long time = data.getTime();
            String dh = String.valueOf(time) + RandomUtil.getRandomLong();
            param.setBackDh(dh);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            param.setThRq(sdf.format(data));
            try {
                int a = mainService.createBCP_TKD(param);
                if (a <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出错");
                } else {
                    BCPTKDResult tkd = new BCPTKDResult();
                    tkd.setBackDh(dh);
                    result.setResult(tkd);
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "半成品退库单创建成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "出错");
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取半成品退库显示数据
     */
    @RequestMapping(value = "/getBcpTkShowData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getBcpTkShowData(String json) {
        BCPTKGetShowDataParam param = ParamsUtils.handleParams(json, BCPTKGetShowDataParam.class);
        ActionResult<BCPTKShowDataResult> result = new ActionResult<BCPTKShowDataResult>();
        if (param != null) {
            try {
                BCPTKShowDataResult showDataResult = mainService.getBCPTKShowData(param.getQrcodeId());
                if (showDataResult == null) {
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
     * @return
     * @author 马鹏昊
     * @desc 半成品退库
     */
    @RequestMapping(value = "/bcpTK", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult bcpTK(String json) {
        BCPTKParam param = ParamsUtils.handleParams(json, BCPTKParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        if (param != null) {
            Date data = new Date();
            long time = data.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            param.setTkTime(sdf.format(data));
            try {
                //首先查找退库单信息
                TKDBCPBean tkdbcpBean = mainService.getTKDBCPBean(param.getBackDh());
                if (tkdbcpBean == null) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "退库单不存在");
                }
                param.setThr(tkdbcpBean.getThr());
                param.setShrr(tkdbcpBean.getShr());
                param.setThDw(tkdbcpBean.getThDw());
                //查找临时库存表信息
                BCPTempSBean bcpTempSBean = mainService.getBcpTempS(param.getQrCodeId());
                if (bcpTempSBean == null)
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "没找到该物料，请先出库");
                param.setProductName(bcpTempSBean.getProductName());
                param.setBcpCode(bcpTempSBean.getBcpCode());
                param.setDwzl(bcpTempSBean.getDwzl());
                param.setSortID(bcpTempSBean.getSortID());
                param.setYlpc(bcpTempSBean.getYlpc());
                param.setScpc(bcpTempSBean.getScpc());
                param.setScTime(bcpTempSBean.getScTime());
                param.setKsTime(bcpTempSBean.getKsTime());
                param.setWcTime(bcpTempSBean.getWcTime());
                param.setZjy(bcpTempSBean.getZjy());
                param.setJyzt(bcpTempSBean.getJyzt());
                param.setCheJian(bcpTempSBean.getCheJian());
                param.setGx(bcpTempSBean.getGx());
                param.setYl1(bcpTempSBean.getYl1());
                param.setYl2(bcpTempSBean.getYl2());
                param.setYl3(bcpTempSBean.getYl3());
                param.setYl4(bcpTempSBean.getYl4());
                param.setYl5(bcpTempSBean.getYl5());
                param.setYl6(bcpTempSBean.getYl6());
                param.setYl7(bcpTempSBean.getYl7());
                param.setYl8(bcpTempSBean.getYl8());
                param.setYl9(bcpTempSBean.getYl9());
                param.setYl10(bcpTempSBean.getYl10());
                //生成退库记录
                int b = mainService.insertBCPBk(param);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "生成退库记录失败");
                } else {
                    //更新仓库半成品库存表数量（退库的数量加上）
                    b = mainService.getBCPSCount(param.getQrCodeId());
                    if (b <= 0) {
                        b = mainService.insertBCPS(param);
                        if (b <= 0) {
                            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "插入半成品库存表数据失败");
                        }
                    } else if (b > 1) {
                        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "半成品库存表记录不唯一");
                    } else {
                        b = mainService.updateBCPSByTk(param);
                        if (b <= 0) {
                            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "修改半成品库存表数据失败");
                        }
                    }
                    //临时库存表中数据减去或者删除
                    if (param.getShl() >= bcpTempSBean.getShl()) {
                        b = mainService.deleteFromBcpTempS(param.getQrCodeId());
                        if (b <= 0) {
                            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "删除半成品临时库存表数据失败");
                        }
                    } else {
                        b = mainService.updateBCPTempSByBCPTk(param);
                        if (b <= 0) {
                            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "修改半成品临时库存表数据失败");
                        }
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
     * @return
     * @author 马鹏昊
     * @desc 生成半成品/成品出库单
     */
    @RequestMapping(value = "/createBCP_CKD", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult createBCP_CKD(String json) {
        CreateBCPCKDParam param = ParamsUtils.handleParams(json, CreateBCPCKDParam.class);
        ActionResult<BCPCKDResult> result = new ActionResult<BCPCKDResult>();
        if (param != null) {
            Date data = new Date();
            long time = data.getTime();
            String dh = String.valueOf(time) + RandomUtil.getRandomLong();
            param.setOutDh(dh);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            param.setLhRq(sdf.format(data));
            try {
                int a = mainService.createBCP_CKD(param);
                if (a <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "出错");
                } else {
                    BCPCKDResult ckd = new BCPCKDResult();
                    ckd.setOutDh(dh);
                    result.setResult(ckd);
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "半成品退库单创建成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
            }
        }
        return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_PARAMS_ERROR, "出错");
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取类别数据
     */
    @RequestMapping(value = "/getLeiBie", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getLeiBie(String json) {
        ActionResult<PdtSortResult> result = new ActionResult<PdtSortResult>();
        try {
            PdtSortResult lbResult = mainService.getPdtSort();
            if (lbResult.getSortBeans() == null || lbResult.getSortBeans().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无类别数据");
            }
            result.setResult(lbResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取类别数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 成品大包装入库
     */
    @RequestMapping(value = "/bigCpIn", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult bigCpIn(String json) {
        BigCPINParam inParam = ParamsUtils.handleParams(json, BigCPINParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        try {
            //查询大包装库存表中是否有数据
            int b = mainService.findCPS2(inParam.getQrCodeId());
            if (b <= 0) {
                //插入大包装库存表（车间）
                b = mainService.insertCPS2(inParam);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "插入CPS2失败");
                }
            } else {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "此大包装已存在");
            }
            b = mainService.insertCPIn2(inParam);
            if (b <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "插入CPIn2失败");
            }
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "成品大包装入库成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取大包装数据
     */
    @RequestMapping(value = "/getBigCpData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getBigCpData(String json) {
        ActionResult<BigCpResult> result = new ActionResult<BigCpResult>();
        try {
            BigCpResult bigCpResult = mainService.getBigCpData();
            if (bigCpResult.getBeans() == null || bigCpResult.getBeans().size() <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "无大包装数据");
            }
            result.setResult(bigCpResult);
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "获取大包装数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 成品小包装入库
     */
    @RequestMapping(value = "/smallCpIn", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult smallCpIn(String json) {
        SmallCPINParam inParam = ParamsUtils.handleParams(json, SmallCPINParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        try {
            //查询小包装库存表中是否有数据
            int b = mainService.findCPS(inParam.getQrCodeId());
            if (b > 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "该成品早已入库，换一个吧");
            }
            b = mainService.insertCPIn(inParam);
            if (b <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "录入CPIn失败");
            }
            String startQrCodeId = inParam.getQrCodeId();
            Long nextQrCodeId = Long.parseLong(startQrCodeId);
            int size = (int) inParam.getShl();
            BigCpBean bigCpBean = null;
            int nowIndex = 0;
            if (!TextUtils.isEmpty(inParam.getcPS2QRCode())) {
                bigCpBean = mainService.getCPS2(inParam.getcPS2QRCode());
                nowIndex = bigCpBean.getNowNum();
            }
            for (int i = 0; i < size; i++) {
                //插入小包装库存表（车间）
                b = mainService.insertCPS(inParam);
                if (b <= 0) {
                    return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "录入CPS失败");
                }
                //如果是需要关联大包装的小包装则需要以下操作
                if (!TextUtils.isEmpty(inParam.getcPS2QRCode())){
                    bigCpBean  = ProjectUtil.getUpdateCPS2Data(bigCpBean,nowIndex+1,nextQrCodeId);
                    b = mainService.updateCPS2(bigCpBean);
                    b = mainService.updateCPIn2(bigCpBean);
                }
                nextQrCodeId += 1;
                nowIndex++;
                inParam.setQrCodeId(String.valueOf(nextQrCodeId));
            }

            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "成品小包装入库成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 大包装出库
     */
    @RequestMapping(value = "/bigCpOut", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult bigCpOut(String json) {
        BigCpOutParam param = ParamsUtils.handleParams(json, BigCpOutParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        param.setFhDate(sdf.format(date));
        try {
            BigCpBean bigCpBean = mainService.getCPS2(param.getQrCodeId());
            param.setCpName(bigCpBean.getcPName());
            param.setCpCode(bigCpBean.getcPCode());
            param.setDwzl(bigCpBean.getDwzl());
            param.setScpc(bigCpBean.getScpc());
            param.setYlpc(bigCpBean.getYlpc());
            param.setSortId(bigCpBean.getSortID());
            int b = mainService.insertCPOut(param);
            if (b<=0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "出库记录生成失败");
            }
            b = mainService.deleteCPS2ByQrId(param.getQrCodeId());
            if (b <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "删除大包装记录失败");
            }
            b = mainService.deleteCPSByCps2QrId(param.getQrCodeId());
            if (b <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "删除大包装关联的小包装记录失败");
            }
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "大包装出库成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取大包装出库显示数据
     */
    @RequestMapping(value = "/getBigCpOutData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getBigCpOutData(String json) {
        BigCpOutGetDataParam param = ParamsUtils.handleParams(json, BigCpOutGetDataParam.class);
        ActionResult<BigCpOutGetDataResult> result = new ActionResult<BigCpOutGetDataResult>();
        if (param != null) {
            try {
                BigCpOutGetDataResult showDataResult = mainService.getCP2ShowData(param.getQrCodeId());
                if (showDataResult == null) {
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
     * @return
     * @author 马鹏昊
     * @desc 小包装出库
     */
    @RequestMapping(value = "/smallCpOut", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult smallCpOut(String json) {
        SmallCpOutParam param = ParamsUtils.handleParams(json, SmallCpOutParam.class);
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        param.setFhDate(sdf.format(date));
        try {
            SmallCpBean bigCpBean = mainService.getCPS(param.getQrCodeId());
            param.setCpName(bigCpBean.getCpName());
            param.setCpCode(bigCpBean.getCpCode());
            param.setDwzl(bigCpBean.getDwzl());
            param.setScpc(bigCpBean.getScpc());
            param.setYlpc(bigCpBean.getYlpc());
            param.setSortId(bigCpBean.getSortID());
            int b = mainService.insertCPOutBySmallParam(param);
            if (b<=0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "出库记录生成失败");
            }
            b = mainService.deleteCPSByQrId(param.getQrCodeId());
            if (b <= 0) {
                return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_MESSAGE_ERROR, "删除小包装记录失败");
            }
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "小包装出库成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "系统异常");
        }
    }

    /**
     * @return
     * @author 马鹏昊
     * @desc 获取小包装出库显示数据
     */
    @RequestMapping(value = "/getSmallCpOutData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getSmallCpOutData(String json) {
        SmallCpOutGetDataParam param = ParamsUtils.handleParams(json, SmallCpOutGetDataParam.class);
        ActionResult<SmallCpOutGetDataResult> result = new ActionResult<SmallCpOutGetDataResult>();
        if (param != null) {
            try {
                SmallCpOutGetDataResult showDataResult = mainService.getSmallCpOutData(param.getQrCodeId());
                if (showDataResult == null) {
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

}
