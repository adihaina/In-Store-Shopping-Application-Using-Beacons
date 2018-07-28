package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 6/30/2018.
 */

public class User_Details {
    String address;
    String loyalty;
    String gender;
    String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User_Details(String address, String loyalty, String gender, String mobile) {
        this.address = address;
        this.loyalty = loyalty;
        this.gender = gender;
        this.mobile=mobile;

    }

    public User_Details() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}


