package com.sstechcanada.resumeapp.models;


public class Order {
    public String name, email, date, timestamp, orderID;


    public Order(String name, String email, String date, String timestamp, String orderID) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.timestamp = timestamp;
        this.orderID = orderID;

    }
}
