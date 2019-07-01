package com.finalProject;
import java.util.Date;

public class Customers {
    //initializing variables
    private int customerNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private String username;
    private String password;

    //getter methods
    public int getCustomerNumber() {
        return customerNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //constructor
    public Customers(int customerNumber, String name, String email, String phoneNumber, String address, Date dateOfBirth, String username, String password){
        this.customerNumber = customerNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
    }


}
