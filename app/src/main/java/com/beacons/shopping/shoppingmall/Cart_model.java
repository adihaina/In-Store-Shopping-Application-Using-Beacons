package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 7/2/2018.
 */

public class Cart_model {
    String product_type,sizes,status;

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cart_model(String product_type, String sizes, String status) {

        this.product_type = product_type;
        this.status=status;

        this.sizes = sizes;
    }

    public Cart_model() {

    }
}
