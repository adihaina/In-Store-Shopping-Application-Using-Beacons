package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 6/19/2018.
 */

class Specification_top {
    String size;
    String fabric;
    String occasion;
    String sleeves;
    String neck;

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

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public Specification_top(String size, String fabric, String occasion, String sleeves, String neck, String fit) {

        this.size = size;
        this.fabric = fabric;
        this.occasion = occasion;
        this.sleeves = sleeves;
        this.neck = neck;
        this.fit = fit;
    }

    String fit;

    public Specification_top(){}



}
