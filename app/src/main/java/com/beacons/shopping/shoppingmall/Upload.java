package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 6/19/2018.
 */

public class Upload {
    String name,brand,price,color,imageurl,offers,rating;

    public Upload()
    {

    }
    public Upload(String name, String brand, String price, String color, String imageurl, String offers, String rating) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.color = color;
        this.imageurl = imageurl;
        this.offers = offers;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
