package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 6/19/2018.
 */

class Specification_suit {
    String size, fabric, occasion, sleeves, dupatta, salwar_type;

    public Specification_suit() {

    }

    public Specification_suit(String size, String fabric, String occasion, String sleeves, String dupatta, String salwar_type) {
        this.size = size;
        this.fabric = fabric;
        this.occasion = occasion;
        this.sleeves = sleeves;
        this.dupatta = dupatta;
        this.salwar_type = salwar_type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getSleeves() {
        return sleeves;
    }

    public void setSleeves(String sleeves) {
        this.sleeves = sleeves;
    }

    public String getDupatta() {
        return dupatta;
    }

    public void setDupatta(String dupatta) {
        this.dupatta = dupatta;
    }

    public String getSalwar_type() {
        return salwar_type;
    }

    public void setSalwar_type(String salwar_type) {
        this.salwar_type = salwar_type;
    }
}