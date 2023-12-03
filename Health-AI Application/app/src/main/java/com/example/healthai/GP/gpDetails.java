package com.example.healthai.GP;

import java.io.Serializable;

public class gpDetails implements Serializable {
    String id, fullname, sex, mobile, specialties;

    public gpDetails() {
    }

    public gpDetails(String id, String fullname, String sex, String mobile, String specialties) {
        this.id = id;
        this.fullname = fullname;
        this.sex = sex;
        this.mobile = mobile;
        this.specialties = specialties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
