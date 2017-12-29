package com.huntkey.rx.ehcache.common.model;

/**
 * Created by sunwei on 2017/12/20 Time:15:47
 */
public class User {

    /**
     * 用户唯一识别号
     */
    private String id;
    /**
     * 用户ID号
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户存款、余额
     */
    private String balance;

//    /** 来自于哪里，默认来自于数据库 */
//    private String from = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", balance='" + balance + '\'' +
                '}';
    }
}
