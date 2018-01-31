package com.qdhualing.qrcodetracker.controll;

import com.alibaba.fastjson.JSONObject;
import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.model.FunctionType;
import com.qdhualing.qrcodetracker.service.MainService;
import com.qdhualing.qrcodetracker.service.UserService;
import com.qdhualing.qrcodetracker.utils.ActionResultUtils;
import com.qdhualing.qrcodetracker.utils.JSONUtils;
import com.qdhualing.qrcodetracker.utils.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/1/26.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/getInputedData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getInputedData(String json) {
        GetNeedInputedDataParams params = ParamsUtils.handleParams(json, GetNeedInputedDataParams.class);
        int type = params.getType();
        switch (type) {
            case FunctionType.MATERIAL_IN:
                DataResult data = mainService.getMaterialInInputedData(params);
                ActionResult<DataResult> result = new ActionResult<DataResult>();
                if (data == null) {
                    result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
                } else {
                    result.setResult(data);
                }
                return result;
            case FunctionType.MATERIAL_OUT:

                break;
            case FunctionType.HALF_PRODUCT_IN:

                break;
            case FunctionType.PRODUCT_IN:

                break;
            case FunctionType.PRODUCT_OUT:

                break;
            case FunctionType.MATERIAL_THROW:

                break;
            case FunctionType.MATERIAL_RETURN:

                break;
        }
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "扫描产品不存在");
        return result;
    }


    @RequestMapping(value = "/commitInputedData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult commitInputedData(String json) {
        DataInputParams params = ParamsUtils.handleListParams(json, DataInputParams.class,"needToInputDataList",DataBean.class);
        int type = params.getType();
        switch (type) {
            case FunctionType.MATERIAL_IN:
                int row = mainService.commitMaterialInputedData(params);
                ActionResult<DataResult> result = new ActionResult<DataResult>();
                if (row < 0) {
                    result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_EXCEPTION, "提交失败");
                } else {
                    result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_SUCCEED, "提交成功");
                }
                return result;
            case FunctionType.MATERIAL_OUT:

                break;
            case FunctionType.HALF_PRODUCT_IN:

                break;
            case FunctionType.PRODUCT_IN:

                break;
            case FunctionType.PRODUCT_OUT:

                break;
            case FunctionType.MATERIAL_THROW:

                break;
            case FunctionType.MATERIAL_RETURN:

                break;
        }
        ActionResult<ActionResult> result = new ActionResult<ActionResult>();
        return result;
    }

}
