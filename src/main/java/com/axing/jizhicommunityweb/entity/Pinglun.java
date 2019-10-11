package com.axing.jizhicommunityweb.entity;

public class Pinglun {
    private Integer id;

    private String plid;

    private String pltimestamp;

    private String plcontent;

    private String uid;

    private String username;

    private String avatar;

    private String vip;

    private Integer praise;

    private Integer zan;

    private Integer del;

    public Pinglun() {
    }

    public Pinglun(Integer id, String plid, String pltimestamp, String plcontent, String uid, String username, String avatar, String vip, Integer praise, Integer zan, Integer del) {
        this.id = id;
        this.plid = plid;
        this.pltimestamp = pltimestamp;
        this.plcontent = plcontent;
        this.uid = uid;
        this.username = username;
        this.avatar = avatar;
        this.vip = vip;
        this.praise = praise;
        this.zan = zan;
        this.del = del;
    }

    public String getPlid() {
        return plid;
    }

    public void setPlid(String plid) {
        this.plid = plid;
    }

    public String getPltimestamp() {
        return pltimestamp;
    }

    public void setPltimestamp(String pltimestamp) {
        this.pltimestamp = pltimestamp;
    }

    public String getPlcontent() {
        return plcontent;
    }

    public void setPlcontent(String plcontent) {
        this.plcontent = plcontent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getZan() {
        return zan;
    }

    public void setZan(Integer zan) {
        this.zan = zan;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pinlun{" +
                "id=" + id +
                ", plid='" + plid + '\'' +
                ", pltimestamp='" + pltimestamp + '\'' +
                ", plcontent='" + plcontent + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", vip='" + vip + '\'' +
                ", praise=" + praise +
                ", zan=" + zan +
                ", del=" + del +
                '}';
    }
}
