package com.example.healthai;

public class UserDetails {
    String name, sex, mobile;
    int age;

    public UserDetails() {
    }

    public UserDetails(String name, String sex, int age, String mobile) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.mobile = mobile;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
