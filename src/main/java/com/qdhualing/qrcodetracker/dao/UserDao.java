package com.qdhualing.qrcodetracker.dao;

import com.qdhualing.qrcodetracker.model.Module2;
import com.qdhualing.qrcodetracker.model.User;
import com.qdhualing.qrcodetracker.model.UserAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */
public interface UserDao {
    User findUserByName(String userName, String password);

    int delete(int id);

    int update(User user);

    UserAuthority getMainData(String userId);

    List<Module2> getAuthNameList(@Param("idList") List<Integer> authList);
}
