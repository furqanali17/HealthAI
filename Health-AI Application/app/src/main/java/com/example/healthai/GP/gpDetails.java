package com.example.healthai.GP;

import java.io.Serializable;

public class gpDetails implements Serializable {
    String id, name, sex, mobile;

    public gpDetails() {
    }

    public gpDetails(String name, String sex, String mobile) {
        this.name = name;
        this.sex = sex;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
