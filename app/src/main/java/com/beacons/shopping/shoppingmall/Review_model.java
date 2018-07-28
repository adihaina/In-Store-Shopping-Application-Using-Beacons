package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 7/4/2018.
 */

public class Review_model {
    String heading,description,username;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Review_model(String heading, String description, String username) {

        this.heading = heading;
        this.description = description;
        this.username = username;
    }

    public Review_model() {

    }

}
