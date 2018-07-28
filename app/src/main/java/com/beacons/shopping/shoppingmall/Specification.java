package com.beacons.shopping.shoppingmall;

/**
 * Created by adi on 6/19/2018.
 */

class Specification {
    String size,fabric,occasion,closure,pockets,fit;

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

    public String getClosure() {
        return closure;
    }

    public void setClosure(String closure) {
        this.closure = closure;
    }

    public String getPockets() {
        return pockets;
    }

    public void setPockets(String pockets) {
        this.pockets = pockets;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public Specification(String size, String fabric, String occasion, String closure, String pockets, String fit) {
        this.size = size;
        this.fabric = fabric;

        this.occasion = occasion;
        this.closure = closure;
        this.pockets = pockets;
        this.fit = fit;
    }
    public Specification(){}

}
