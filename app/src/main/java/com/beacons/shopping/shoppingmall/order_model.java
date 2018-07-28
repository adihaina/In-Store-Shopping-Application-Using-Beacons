package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 7/4/2018.
 */

public class order_model {
    String image_url;
    String product_name;
    String product_type;
    String price;
    String size;
    String product_id;
    String status;
    String rate;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public order_model(String image_url, String product_name, String product_type, String price, String size, String product_id, String status, String rate) {
        this.image_url=image_url;

        this.product_name = product_name;
        this.product_type = product_type;
        this.price = price;
        this.size = size;
        this.product_id = product_id;
        this.status=status;
        this.rate=rate;
    }

    public order_model() {

    }
}
