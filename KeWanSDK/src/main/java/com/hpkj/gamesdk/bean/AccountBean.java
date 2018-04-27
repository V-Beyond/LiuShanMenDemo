package com.hpkj.gamesdk.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * @ClassNname：AccountBean.java
 * @Describe 用户信息实体类
 * @author huanglei
 * @time 2018/4/9 15:57
 */
@Table(name = "account")
public class AccountBean {
    //指明字段,主键,是否自增长,约束(不能为空)
    @Column(name = "id",isId = true,autoGen = false)
    private int id;
    @Column(name = "phone")
    private  String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "saveTime")
    private long saveTime;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public AccountBean(){

    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", saveTime='" + saveTime + '\'' +
                '}';
    }
}
