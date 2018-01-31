package com.qdhualing.qrcodetracker.controll;

import com.qdhualing.qrcodetracker.bean.ActionResult;
import com.qdhualing.qrcodetracker.bean.LoginParams;
import com.qdhualing.qrcodetracker.bean.LoginResult;
import com.qdhualing.qrcodetracker.model.User;
import com.qdhualing.qrcodetracker.service.UserService;
import com.qdhualing.qrcodetracker.utils.ActionResultUtils;
import com.qdhualing.qrcodetracker.utils.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
            loginResult.setUserId("" + user.getId());
            loginResult.setUserName("" + user.getUserName());
            loginResult.setTrueName("" + user.getTrueName());
            result.setResult(loginResult);
        }
        return result;
    }

}
