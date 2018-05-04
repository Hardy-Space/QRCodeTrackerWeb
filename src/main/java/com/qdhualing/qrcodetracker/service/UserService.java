package com.qdhualing.qrcodetracker.service;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.dao.UserDao;
import com.qdhualing.qrcodetracker.model.Module2;
import com.qdhualing.qrcodetracker.model.User;
import com.qdhualing.qrcodetracker.model.UserAuthority;
import com.qdhualing.qrcodetracker.utils.AuthorityParseUtil;
import com.qdhualing.qrcodetracker.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */

@Service
public class UserService implements Serializable{

    @Resource
    private UserDao userDao;

    public User login(LoginParams params) {
        if (TextUtils.isEmpty(params.getUserName())||TextUtils.isEmpty(params.getPassword())){
            return null;
        }
        User result = userDao.findUserByName(params.getUserName(),params.getPassword());
        if (result!=null){
            return result;
        }
        return null;
    }

    public MainResult getMainData(MainParams params) {

        UserAuthority userAuthority = userDao.getMainData(params.getUserId());
        MainResult mainResult = new MainResult();
        mainResult.setDepartmentName(userAuthority.getGroupName());
        List<Integer> authList = AuthorityParseUtil.parseToFunctionList(userAuthority.getAuthMobile(),userAuthority.getGroup2());

        List<Module2> authNameList = new ArrayList<>();
        if (authList == null||authList.size()<=0) {
            mainResult.setCanUseFunctionList(authNameList);
            return mainResult;
        }

        authNameList = userDao.getAuthNameList(authList);
        mainResult.setCanUseFunctionList(authNameList);
        return mainResult;
    }
}
