package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 6/19/2018.
 */

class Specification_footwear {
    String size, material, occasion, closure, shoe_type, tip_shape;

    public Specification_footwear(String size, String material, String occasion, String closure, String shoe_type, String tip_shape) {
        this.size = size;
        this.material = material;
        this.occasion = occasion;
        this.closure = closure;
        this.shoe_type = shoe_type;
        this.tip_shape = tip_shape;
    }
    public Specification_footwear(){}

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getClosure() {
        return closure;
    }

    public void setClosure(String closure) {
        this.closure = closure;
    }

    public String getShoe_type() {
        return shoe_type;
    }

    public void setShoe_type(String shoe_type) {
        this.shoe_type = shoe_type;
    }

    public String getTip_shape() {
        return tip_shape;
    }

    public void setTip_shape(String tip_shape) {
        this.tip_shape = tip_shape;
    }
}