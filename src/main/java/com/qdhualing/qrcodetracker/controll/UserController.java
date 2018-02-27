package com.qdhualing.qrcodetracker.controll;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.model.User;
import com.qdhualing.qrcodetracker.model.UserAuthority;
import com.qdhualing.qrcodetracker.service.UserService;
import com.qdhualing.qrcodetracker.utils.ActionResultUtils;
import com.qdhualing.qrcodetracker.utils.AuthorityParseUtil;
import com.qdhualing.qrcodetracker.utils.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult login(String json) {
        LoginParams params = ParamsUtils.handleParams(json, LoginParams.class);
        User user = userService.login(params);
        ActionResult<LoginResult> result = new ActionResult<LoginResult>();
        if (user == null) {
            result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "用户不存在或密码错误");
        } else {
            LoginResult loginResult = new LoginResult();
            loginResult.setUserId("" + user.getUserID());
            loginResult.setUserName(user.getLoginName());
            loginResult.setTrueName(user.getTrueName());
//            loginResult.setPortraitUrl(user.getTrueName());
            result.setResult(loginResult);
        }
        return result;
    }

    @RequestMapping(value = "/getMainData", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult getMainData(String json) {
        MainParams params = ParamsUtils.handleParams(json, MainParams.class);
        MainResult mainResult = userService.getMainData(params);
        ActionResult<MainResult> result = new ActionResult<MainResult>();
        if (mainResult.getCanUseFunctionList().size()<=0) {
            result = ActionResultUtils.setResultMsg(result, ActionResult.STATUS_LOGIC_ERROR, "未查询到相关权限");
        } else {
            result.setResult(mainResult);
        }
        return result;
    }

}
