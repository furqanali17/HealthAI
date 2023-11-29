package com.example.healthai.Profile;

public class UserDetails {
    String name, sex, mobile, email;
    int age;

    public UserDetails() {
    }

    public UserDetails(String name, String sex, int age, String mobile, String email) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
