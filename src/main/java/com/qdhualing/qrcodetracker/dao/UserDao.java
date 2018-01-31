package com.qdhualing.qrcodetracker.dao;

import com.qdhualing.qrcodetracker.model.User;

/**
 * Created by Administrator on 2018/1/27.
 */
public interface UserDao {
    public User findUserByName(String userName);
    public int delete(int id);
    public int update(User user);

}
