package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.LoginParams;
import com.qdhualing.qrcodetracker.bean.LoginResult;
import com.qdhualing.qrcodetracker.bean.User;
import com.qdhualing.qrcodetracker.dao.UserDao;
import com.qdhualing.qrcodetracker.utils.TextUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/27.
 */

@Service
public class UserService implements Serializable{

    @Resource
    private UserDao userDao;

    public User login(LoginParams params){
        if (TextUtils.isEmpty(params.getUserName())
                ||TextUtils.isEmpty(params.getPassword())){
            return null;
        }
        User user = userDao.findUserByName(params.getUserName());
        if (user!=null&&params.getPassword().equals(user.getPwd())){
            return user;
        }
        return null;
    }
}
