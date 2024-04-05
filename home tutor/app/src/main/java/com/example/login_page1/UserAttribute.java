package com.example.login_page1;

public class UserAttribute {
    String fName, lName, city, address;
    int age;

    public UserAttribute(String fName, String lName, String city, String address, int age) {
        this.fName = fName;
        this.lName = lName;
        this.city = city;
        this.address = address;
        this.age = age;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public UserAttribute() {
    }
}
