package com.qdhualing.qrcodetracker.model;

/**
 * @author 马鹏昊
 * @desc 物料库存表
 * @date 2018/1/30
 */
public class M_WLS {

    //物料入库记录id
    private int id ;

    //二维码id
    private String qrcodeId ;

    //产品名称
    private String productName ;

    //分类id
    private int sortId ;

    //物料编码（分三级，一级一位，二级2位，三级4位）
    private String wlCode ;

    //原料批次
    private String ylpc ;

    //批次总量
    private float pczl ;

    //来料时间
    private String llTime ;

    //单位重量
    private float dwzl ;

    //数量
    private float shl ;

    //序号
    private int xh ;

    //产地
    private String chd ;

    //规格
    private String gg ;

    //操作员
    private String czy ;

    //单位
    private String company ;

    //库存预警
    private float yuJing ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQrcodeId() {
        return qrcodeId;
    }

    public void setQrcodeId(String qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getWlCode() {
        return wlCode;
    }

    public void setWlCode(String wlCode) {
        this.wlCode = wlCode;
    }

    public String getYlpc() {
        return ylpc;
    }

    public void setYlpc(String ylpc) {
        this.ylpc = ylpc;
    }

    public float getPczl() {
        return pczl;
    }

    public void setPczl(float pczl) {
        this.pczl = pczl;
    }

    public String getLlTime() {
        return llTime;
    }

    public void setLlTime(String llTime) {
        this.llTime = llTime;
    }

    public float getDwzl() {
        return dwzl;
    }

    public void setDwzl(float dwzl) {
        this.dwzl = dwzl;
    }

    public float getShl() {
        return shl;
    }

    public void setShl(float shl) {
        this.shl = shl;
    }

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public String getChd() {
        return chd;
    }

    public void setChd(String chd) {
        this.chd = chd;
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public String getCzy() {
        return czy;
    }

    public void setCzy(String czy) {
        this.czy = czy;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getYuJing() {
        return yuJing;
    }

    public void setYuJing(float yuJing) {
        this.yuJing = yuJing;
    }
}
