package com.finalProject;

public class Inventory {
    //initializing variables
    private int itemNumber;
    private String modelName;
    private String color;
    private float price;
    private String size;
    private int quantity;

    //getter methods
    public int getItemNumber() {
        return itemNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public String getColor() {
        return color;
    }

    public float getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    //constructor
    public Inventory(int itemNumber, String modelName, String color, float price, String size, int quantity) {
        this.itemNumber = itemNumber;
        this.modelName = modelName;
        this.color = color;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

}
