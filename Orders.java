package com.finalProject;
import java.util.Date;

public class Orders {
    //initializing variables
    private int orderNumber;
    private int customerNumber;
    private int itemNumber;
    private int quantity;
    private Date date;
    private String status;

    //getter methods
    public int getOrderNumber() {
        return orderNumber;
    }


    public int getCustomerNumber() {
        return customerNumber;
    }


    public int getItemNumber() {
        return itemNumber;
    }


    public int getQuantity() {
        return quantity;
    }


    public Date getDate() {
        return date;
    }


    public String getStatus() {
        return status;
    }


    //constructor
    public Orders(int orderNumber, int customerNumber, int itemNumber, int quantity, Date date, String status){
        this.orderNumber = orderNumber;
        this.customerNumber = customerNumber;
        this.itemNumber = itemNumber;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }


}
