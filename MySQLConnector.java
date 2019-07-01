package com.finalProject;
import java.sql.*;
import java.util.ArrayList;

public class MySQLConnector {

    private Connection conn;

    public MySQLConnector(String host, String username, String password){
        //gets connection with database
        try{
            conn = DriverManager.getConnection("jdbc:" + host, username, password);
        }
        catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    //used to search or display tables
    public ResultSet execQuery(String object){
        ResultSet resSet = null;

        try{
            Statement statement = conn.createStatement();
            resSet = statement.executeQuery(object);
        }
        catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }

        return resSet;

    }

    //used to add/update/delete tables
    public boolean execUpdate(String object){
        int rowsAffected = 0;

        try{
            Statement statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(object);
        }
        catch(SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
        return rowsAffected > 0;
    }

    //arraylist to store data of inventory
    public ArrayList<Inventory> getInventory(){
        ArrayList<Inventory> inventory = new ArrayList<Inventory>();

        Statement s;
        ResultSet resSet;
        Inventory i;

        try{
            s = conn.createStatement();
            resSet = s.executeQuery("SELECT * FROM inventory");

            while(resSet.next()){
                i = new Inventory(resSet.getInt("itemNumber"), resSet.getString("modelname"), resSet.getString("color"), resSet.getFloat("price"), resSet.getString("size"), resSet.getInt("quantity"));
                inventory.add(i);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    //arraylist to store data of customers
    public ArrayList<Customers> getCustomers(){
        ArrayList<Customers> customers = new ArrayList<Customers>();

        Statement s;
        ResultSet resSet;
        Customers c;

        try{
            s = conn.createStatement();
            resSet = s.executeQuery("SELECT * FROM customers");

            while(resSet.next()){
                c = new Customers(resSet.getInt("customerNumber"), resSet.getString("name"), resSet.getString("email"), resSet.getString("phoneNumber"), resSet.getString("address"), resSet.getDate("dateOfBirth"), resSet.getString("username"), resSet.getString("password"));
                customers.add(c);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //arraylist to store data of orders
    public ArrayList<Orders> getOrders(){
        ArrayList<Orders> orders = new ArrayList<Orders>();

        Statement s;
        ResultSet resSet;
        Orders o;

        try{
            s = conn.createStatement();
            resSet = s.executeQuery("SELECT * FROM orders");

            while(resSet.next()){
                o = new Orders(resSet.getInt("orderNumber"), resSet.getInt("customerNumber"), resSet.getInt("itemNumber"), resSet.getInt("quantity"), resSet.getDate("date"), resSet.getString("status"));
                orders.add(o);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
