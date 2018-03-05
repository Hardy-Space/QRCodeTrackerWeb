package com.qdhualing.qrcodetracker.daoI;

import com.qdhualing.qrcodetracker.bean.*;
import com.qdhualing.qrcodetracker.dao.MainDao;
import com.qdhualing.qrcodetracker.model.AppInputPropertyHolder;
import com.qdhualing.qrcodetracker.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/29.
 */
@Repository
public class SqlServerMainDao  {

    @Autowired
    private JDBCDataSource dataSource;


    public DataResult getMaterialInData(String qrcodeId) {
        String sql = "select * from M_WLIn where QRCode_ID=?";
        Connection conn;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, qrcodeId);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            DataResult dataResult = null;
            List<DataBean> beanList = null;
            while (rs.next()) {
                //初始化需要录入的参数名
                AppInputPropertyHolder.initWlinPropertyMap();
                dataResult = new DataResult();
                beanList = new ArrayList<DataBean>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    DataBean b = new DataBean();
                    String columnName = rsmd.getColumnName(i);
                    b.setSqlFieldName(columnName);
                    int dataType = rsmd.getColumnType(i);
                    b.setDataType(dataType);
                    String value = null;
                    switch (dataType) {
                        case Types.BIGINT:
                            value = String.valueOf(rs.getInt(columnName));
                            break;
                        case Types.INTEGER:
                            value = String.valueOf(rs.getInt(columnName));
                            break;
                        case Types.FLOAT:
                            value = String.valueOf(rs.getFloat(columnName));
                            break;
                        case Types.DOUBLE:
                            value = String.valueOf(rs.getDouble(columnName));
                            break;
                        case Types.VARCHAR:
                            value = rs.getString(columnName);
                            break;
                        case Types.TIME:
                            value = String.valueOf(rs.getTime(columnName));
                            break;
                    }
                    b.setValue(value);
                    b.setName(AppInputPropertyHolder.mWlinPropertyMap.get(columnName));
                    beanList.add(b);
                }
            }
            //把库存表中有的而入库记录表中没有的填上
            String sql2 = "select Company,YuJing from M_WLS where QRCode_ID=?";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, qrcodeId);
            rs = ps.executeQuery();
            DataBean b0 = new DataBean();
            b0.setName(AppInputPropertyHolder.mWlinPropertyMap.get("Company"));
            b0.setSqlFieldName("Company");
            b0.setDataType(Types.VARCHAR);
            DataBean b1 = new DataBean();
            b1.setName(AppInputPropertyHolder.mWlinPropertyMap.get("YuJing"));
            b1.setSqlFieldName("YuJing");
            b1.setDataType(Types.FLOAT);
            if (rs.next()) {
                b0.setValue(rs.getString("Company"));
                b1.setValue(String.valueOf(rs.getFloat("YuJing")));
            }
            beanList.add(b0);
            beanList.add(b1);
            dataResult.setNeedToInputDataList(beanList);

            rs.close();
            ps.close();
            conn.close();
            return dataResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int commitMaterialInputedData(DataInputParams params) {
        //组建更新入库记录表的sql语句
        StringBuffer setSBuf = new StringBuffer();
        List<DataBean> ll = params.getNeedToInputDataList();
        String wlcode = null;
        String ylpc = null;
        String qrcodeId = null;
        for (int i = 0; i < ll.size(); i++) {
            DataBean b = ll.get(i);

            //主键不允许修改
            if ("ID".equals(b.getSqlFieldName())) {
                continue;
            }
            //如果是库存表中的字段则舍弃
            if ("Company".equals(b.getSqlFieldName())) {
                continue;
            }
            if ("YuJing".equals(b.getSqlFieldName())) {
                continue;
            }
            //扫码入库标志位设置为1
            if ("Bz".equals(b.getSqlFieldName())) {
                b.setValue("1");
            }
            setSBuf.append(b.getSqlFieldName() + "='" + b.getValue() + "',");
            if ("WL_Code".equals(b.getSqlFieldName())) {
                wlcode = b.getValue();
            } else if ("YLPC".equals(b.getSqlFieldName())) {
                ylpc = b.getValue();
            } else if ("QRCode_ID".equals(b.getSqlFieldName())) {
                qrcodeId = b.getValue();
            }
        }
        String setStr = setSBuf.deleteCharAt(setSBuf.length() - 1).toString();
        String sql = "UPDATE M_WLIn SET " + setStr + " where WL_Code=? and YLPC=?";
        Connection conn;
        int row = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, wlcode);
            ps.setString(2, ylpc);
            int rr = ps.executeUpdate();
            if (rr > 0) {
                row = rr;
            } else {
                //如果入库记录表里没有记录则生成一条
                setSBuf = new StringBuffer();
                StringBuffer columnBuf = new StringBuffer();
                String columnStr = null;
                for (int i = 0; i < ll.size(); i++) {
                    DataBean b = ll.get(i);

                    //主键不允许修改
                    if ("ID".equals(b.getSqlFieldName())) {
                        continue;
                    }
                    //如果是库存表中的字段则舍弃
                    if ("Company".equals(b.getSqlFieldName())) {
                        continue;
                    }
                    if ("YuJing".equals(b.getSqlFieldName())) {
                        continue;
                    }
                    columnBuf.append(b.getSqlFieldName() + ",");
                    setSBuf.append("'" + b.getValue() + "',");
                }
                setStr = setSBuf.deleteCharAt(setSBuf.length() - 1).toString();
                columnStr = columnBuf.deleteCharAt(columnBuf.length() - 1).toString();
                sql = "INSERT INTO M_WLIn (" + columnStr + ") VALUES (" + setStr + ")";
                ps = conn.prepareStatement(sql);
                rr = ps.executeUpdate();
                if (rr > 0) {
                    row = rr;
                }
            }

            //查询是否库存表是否存有记录
            sql = "select * from M_WLS where QRCode_ID=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, qrcodeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setSBuf = new StringBuffer();
                for (int i = 0; i < ll.size(); i++) {
                    DataBean b = ll.get(i);
                    if ("SHL".equals(b.getSqlFieldName())) {
                        //如果是数量那么加上然后更新
                        float addedShl = Float.valueOf(b.getValue());
                        float newShl = addedShl + rs.getFloat("SHL");
                        setSBuf.append(b.getSqlFieldName() + "='" + newShl + "',");
                    } else {
                        //主键不允许修改
                        if ("ID".equals(b.getSqlFieldName())) {
                            continue;
                        }
                        //如果是入库单号则舍弃
                        if ("InDh".equals(b.getSqlFieldName())) {
                            continue;
                        }
                        //如果是扫码入库标志位则舍弃
                        if ("Bz".equals(b.getSqlFieldName())) {
                            continue;
                        }
                        //如果是其他字段那么直接更新
                        setSBuf.append(b.getSqlFieldName() + "='" + b.getValue() + "',");
                    }
                }
                setStr = setSBuf.deleteCharAt(setSBuf.length() - 1).toString();
                sql = "UPDATE M_WLS set " + setStr + " where QRCode_ID=?";
                ps = conn.prepareStatement(sql);
                rr = ps.executeUpdate();
                if (rr>0) {
                    row = rr;
                }
            } else {//如果没有记录则生成一条新的
                setSBuf = new StringBuffer();
                StringBuffer columnBuf = new StringBuffer();
                String columnStr = null;
                for (int i = 0; i < ll.size(); i++) {
                    DataBean b = ll.get(i);
                    //主键不允许修改
                    if ("ID".equals(b.getSqlFieldName())) {
                        continue;
                    }
                    //如果是入库单号则舍弃
                    if ("InDh".equals(b.getSqlFieldName())) {
                        continue;
                    }
                    //如果是扫码入库标志位则舍弃
                    if ("Bz".equals(b.getSqlFieldName())) {
                        continue;
                    }
                    columnBuf.append(b.getSqlFieldName() + ",");
                    setSBuf.append("'" + b.getValue() + "',");
                }
                setStr = setSBuf.deleteCharAt(setSBuf.length() - 1).toString();
                columnStr = columnBuf.deleteCharAt(columnBuf.length() - 1).toString();
                sql = "INSERT INTO M_WLS (" + columnStr + ") VALUES (" + setStr + ")";
                ps = conn.prepareStatement(sql);
                rr = ps.executeUpdate();
                if (rr>0) {
                    row = rr;
                }
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }


    public DataResult getMaterialOutData(String qrcodeId) {
        return null;
    }

    public DataResult getHalfProductInData(String qrcodeId) {
        return null;
    }

    public DataResult getProductInData(String qrcodeId) {
        return null;
    }

    public DataResult getProductOutData(String qrcodeId) {
        return null;
    }

    public DataResult getMaterialThrowData(String qrcodeId) {
        return null;
    }

    public DataResult getMaterialReturnData(String qrcodeId) {
        return null;
    }

}
