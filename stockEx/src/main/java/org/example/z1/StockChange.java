package org.example.z1;

import org.example.grpc.Company;

public class StockChange {
    private Company comp;
    private double oldPrice;
    private double newPrice;


    public StockChange(Company comp) {
        this.comp = comp;
        this.newPrice = comp.getPriceMorning();
    }

    public Company getComp() {
        return comp;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }



    public void setPriceNew(double price1h) {
        this.newPrice = price1h;
    }

    public void setPriceOld(double price24h) {
        this.oldPrice = price24h;
    }


}
