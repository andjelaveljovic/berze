package org.example.z1;

import java.util.Random;

public class Client {
    private double money;
    private String idClient;
    Random random = new Random();

    public Client(String ime) {
        this.money = 5000 + (1100000 - 5000) * random.nextDouble();
        this.idClient = ime;

    }

    public double getMoney() {
        return money;
    }
    public String getID() {
        return idClient;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public void setID(String id) {
        this.idClient = id;
    }
}
