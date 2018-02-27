package com.qdhualing.qrcodetracker.daoI;

import com.qdhualing.qrcodetracker.bean.JDBCDataSource;
import com.qdhualing.qrcodetracker.model.User;
import com.qdhualing.qrcodetracker.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/1/27.
 */
//@Repository
//public class SqlServerUserDao implements UserDao,Serializable {
//
////    @Autowired
//    private JDBCDataSource dataSource ;
//
//    public User findUserByName(String userName) {
//        String sql = "select * from Hl_User where LoginName=?";
//        Connection conn ;
//        try {
//            conn = dataSource.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1,userName);
//            ResultSet rs = ps.executeQuery();
//            User user = null;
//            while (rs.next()){
//                user = new User();
//                user.setId(rs.getInt("UserID"));
//                user.setUserName(rs.getString("LoginName"));
//                user.setPwd(rs.getString("Password"));
//                user.setTrueName(rs.getString("TrueName"));
//            }
//            rs.close();
//            ps.close();
//            return user;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public int delete(int id) {
//        return 0;
//    }
//
//    public int update(User user) {
//        return 0;
//    }
//}
