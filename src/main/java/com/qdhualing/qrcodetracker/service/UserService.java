package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.dao.UserDao;
import com.qdhualing.qrcodetracker.model.User;
import com.qdhualing.qrcodetracker.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/27.
 */

@Service
public class UserService implements Serializable{

    @Autowired
    private UserDao userDao;

    public User login(LoginParams params) {
        if (TextUtils.isEmpty(params.getUserName())||TextUtils.isEmpty(params.getPassword())){
            return null;
        }
        User result = userDao.findUserByName(params.getUserName());
        if (result!=null){
            return result;
        }
        return null;

    }
}
