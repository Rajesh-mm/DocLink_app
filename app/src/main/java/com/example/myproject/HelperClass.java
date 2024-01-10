package com.example.myproject;

public class HelperClass {


    private String name, email, password, day, month, year, mobileNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public HelperClass(String name, String email, String password, String day, String month, String year, String mobileNo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.day = day;
        this.month = month;
        this.year = year;
        this.mobileNo = mobileNo;
    }

}


