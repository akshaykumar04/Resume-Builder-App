package com.sstechcanada.resumeapp.models;


public class Order {
    public String name, email, date, timestamp, orderID;




    public Order(){
        //this constructor is required
    }

    public Order(String name, String email, String date, String timestamp, String orderID) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.timestamp = timestamp;
        this.orderID = orderID;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}