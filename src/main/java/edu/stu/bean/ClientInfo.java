package edu.stu.bean;

import java.util.Date;

public class ClientInfo {
    private String userName;
    private String ip;
    private Date date;
    private Boolean tag;

    public ClientInfo(String userName, String ip, Date date, Boolean tag) {
        this.userName = userName;
        this.ip = ip;
        this.date = date;
        this.tag = tag;
    }

    public String getUserName() {
        return userName;
    }

    public String getIp() {
        return ip;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }
}
