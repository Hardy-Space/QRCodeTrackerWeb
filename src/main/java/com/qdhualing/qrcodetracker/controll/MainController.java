package com.qdhualing.qrcodetracker.controll;

import com.qdhualing.qrcodetracker.bean.ActionResult;
import com.qdhualing.qrcodetracker.bean.LoginParams;
import com.qdhualing.qrcodetracker.bean.LoginResult;
import com.qdhualing.qrcodetracker.bean.User;
import com.qdhualing.qrcodetracker.service.UserService;
import com.qdhualing.qrcodetracker.utils.ActionResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/1/26.
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ActionResult login(LoginParams params) {
        User user = userService.login(params);
        ActionResult<LoginResult> result = new ActionResult<LoginResult>();
        if (user == null){
            result = ActionResultUtils.setErrorResult(result,ActionResult.STATUS_LOGIC_ERROR,"用户名或密码错误");
        }else{
            LoginResult loginResult = new LoginResult();
            loginResult.setUserId(""+user.getId());
            result.setResult(loginResult);
        }
        return result;
    }

}
