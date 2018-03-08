package com.qdhualing.qrcodetracker.bean;

/**
 * @author 马鹏昊
 * @desc
 * @date 2018/3/8
 */
public class WLSBean {

    //二维码编号
    private String QRCodeID;
    //物料编码
    private String WLCode;
    //货物名称
    private String ProductName;
    //类别
    private Integer SortID;
    //原料批次
    private String YLPC;
    //数量
    private float SHL ;
    //单位重量
    private float DWZL;
    //批次重量
    private float PCZL;
    //单位
    private String DW ;
    //规格
    private String GG;
    //产地
    private String CHD;
    //来料时间（入库时间）
    private String LLTime;
    //操作员（入库人）
    private String CZY;

    public String getQRCodeID() {
        return QRCodeID;
    }

    public void setQRCodeID(String QRCodeID) {
        this.QRCodeID = QRCodeID;
    }

    public String getWLCode() {
        return WLCode;
    }

    public void setWLCode(String WLCode) {
        this.WLCode = WLCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Integer getSortID() {
        return SortID;
    }

    public void setSortID(Integer sortID) {
        SortID = sortID;
    }

    public String getYLPC() {
        return YLPC;
    }

    public void setYLPC(String YLPC) {
        this.YLPC = YLPC;
    }

    public float getSHL() {
        return SHL;
    }

    public void setSHL(float SHL) {
        this.SHL = SHL;
    }

    public float getDWZL() {
        return DWZL;
    }

    public void setDWZL(float DWZL) {
        this.DWZL = DWZL;
    }

    public float getPCZL() {
        return PCZL;
    }

    public void setPCZL(float PCZL) {
        this.PCZL = PCZL;
    }

    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }

    public String getGG() {
        return GG;
    }

    public void setGG(String GG) {
        this.GG = GG;
    }

    public String getCHD() {
        return CHD;
    }

    public void setCHD(String CHD) {
        this.CHD = CHD;
    }

    public String getLLTime() {
        return LLTime;
    }

    public void setLLTime(String LLTime) {
        this.LLTime = LLTime;
    }

    public String getCZY() {
        return CZY;
    }

    public void setCZY(String CZY) {
        this.CZY = CZY;
    }
}
