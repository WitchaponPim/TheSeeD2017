package com.example.ptwitchapon.theseed2017.Model;

import com.google.gson.annotations.Expose;

/**
 * Created by ptwitchapon on 18/12/2560.
 */

public class User {

    /**
     * runno : 3
     * username : ptwitchapon
     * password : lumpini
     * name : วิชญ์พล
     * lastname : พิมพ์รัตน์
     * picpath : pic/LPN00207988.JPG
     * major : MIS
     * group : Programmer
     */
    @Expose
    private String runno;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String name;
    @Expose
    private String lastname;
    @Expose
    private String picpath;
    @Expose
    private String major;
    @Expose
    private String group;
    @Expose
    private String id_user;


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getRunno() {
        return runno;
    }

    public void setRunno(String runno) {
        this.runno = runno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
