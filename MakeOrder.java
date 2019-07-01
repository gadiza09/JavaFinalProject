package com.finalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakeOrder extends JFrame implements ActionListener {
    //connects to database
    MySQLConnector connector = new MySQLConnector("mysql://localhost/FinalProject?serverTimezone=GMT", "root", "gadiza");

    //initializing variables
    private int orderNumber;
    private int customerNumber;
    private int itemNumber;
    private int quantity;
    private Date date;
    private String theDate;
    private String status = "Pending";
    private boolean checkOrders = false;
    private boolean checkDataType1 = false;
    private boolean checkDataType2 = false;
    private boolean checkDataType3 = false;
    private boolean checkDataType4 = false;
    private int frameHeight;

    public MakeOrder(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Make Order");

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //setting the frame
        Container c = getContentPane(); //ensures that there is only one content pane
        c.setBackground(Color.PINK);
        c.setLayout(new BorderLayout());

        //panels to organize frame
        JPanel tablePanel1 = new JPanel(new BorderLayout());
        JPanel tablePanel2 = new JPanel(new BorderLayout());
        JPanel tablesPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());

        //label for heading
        JLabel titleLabel = new JLabel("Make an Order", SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.BLACK);

        //all the input labels and input textfields, added to different panels to keep it organized
        JLabel custNo = new JLabel("Customer Number: ");
        custNo.setOpaque(false);
        custNo.setForeground(Color.BLACK);

        JTextField inputCustNo = new JTextField(10);

        panel1.add(inputCustNo, SwingConstants.CENTER);
        panel1.add(custNo, SwingConstants.CENTER);
        panel1.setBackground(Color.PINK);

        JLabel itemNo = new JLabel("Item Number: ");
        custNo.setOpaque(false);
        custNo.setForeground(Color.BLACK);

        JTextField inputItemNo = new JTextField(10);

        panel2.add(inputItemNo, SwingConstants.CENTER);
        panel2.add(itemNo, SwingConstants.CENTER);
        panel2.setBackground(Color.PINK);

        JLabel qty = new JLabel("Quantity: ");
        qty.setOpaque(false);
        qty.setForeground(Color.BLACK);

        JTextField inputQty = new JTextField(10);

        panel3.add(inputQty, SwingConstants.CENTER);
        panel3.add(qty, SwingConstants.CENTER);
        panel3.setBackground(Color.PINK);

        JLabel orderDate = new JLabel("Date (YYYY-MM-DD): ");
        orderDate.setOpaque(false);
        orderDate.setForeground(Color.BLACK);

        JTextField inputDate = new JTextField(10);

        panel4.add(inputDate, SwingConstants.CENTER);
        panel4.add(orderDate, SwingConstants.CENTER);
        panel4.setBackground(Color.PINK);

        //button to confirm order
        JButton confirmOrder = new JButton("Confirm Order");
        confirmOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfields
                String confirm = e.getActionCommand();
                String theCustNo = inputCustNo.getText();
                String theItemNo = inputItemNo.getText();
                String theQty = inputQty.getText();
                theDate = inputDate.getText();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                //validates inputs
                try{
                    customerNumber = Integer.parseInt(theCustNo);
                    checkDataType1 = true;
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please input Integer!");
                    checkDataType1 = false;
                }

                try{
                    itemNumber = Integer.parseInt(theItemNo);
                    checkDataType2 = true;
                }
                catch(NumberFormatException n){
                    checkDataType2 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }

                try{
                    quantity = Integer.parseInt(theQty);
                    checkDataType3 = true;
                }
                catch(NumberFormatException n){
                    checkDataType3 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }

                try{
                    date = dateFormat.parse(theDate);
                    checkDataType4 = true;
                }
                catch(ParseException p){
                    JOptionPane.showMessageDialog(null, "Please input date with correct format!");
                    checkDataType4 = false;
                }

                if(confirm.equals("Confirm Order")){
                    //query for MySQL
                    ResultSet rs = connector.execQuery(String.format("SELECT orderNumber FROM orders WHERE orderNumber = '%d'", orderNumber));

                    try{
                        if(rs.next()){
                            //checking if order number exists
                            checkOrders = false;
                            JOptionPane.showMessageDialog(null, "Order Number is taken!");
                        }
                        else{
                            checkOrders = true;
                        }
                    }
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    //only inputs if all the validations are checked
                    if(checkDataType1  && checkDataType2 && checkDataType3 && checkDataType4 && checkOrders){
                        //query for MySQL
                        connector.execUpdate(String.format("INSERT INTO orders (orderNumber, customerNumber, itemNumber, quantity, date, status) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", orderNumber, customerNumber, itemNumber, quantity, theDate, status));
                        JOptionPane.showMessageDialog(null, "Order Successfully Added!");
                    }
                }

            }
        });

        //button to go back to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(this);

        //button to refresh
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(this);

        //display table
        DefaultTableModel ordersDisplay = new DefaultTableModel();
        JTable ordersTable = new JTable(ordersDisplay);
        //column names
        ordersDisplay.addColumn("Order Number");
        ordersDisplay.addColumn("Customer Number");
        ordersDisplay.addColumn("Item Number");
        ordersDisplay.addColumn("Quantity");
        ordersDisplay.addColumn("Date");
        ordersDisplay.addColumn("Status");
        ArrayList<Orders> orders = connector.getOrders();
        //shows all rows
        Object[] rows = new Object[6];
        for(int i = 0; i<orders.size(); i++){
            rows[0] = orders.get(i).getOrderNumber();
            rows[1] = orders.get(i).getCustomerNumber();
            rows[2] = orders.get(i).getItemNumber();
            rows[3] = orders.get(i).getQuantity();
            rows[4] = orders.get(i).getDate();
            rows[5] =orders.get(i).getStatus();
            ordersDisplay.addRow(rows);
        }
        ordersTable.setShowGrid(true);
        orderNumber = orders.size() + 1;

        //table name
        JLabel ordersTitle = new JLabel("Orders:");

        //organizing panels
        tablePanel1.add(ordersTitle, BorderLayout.NORTH);
        tablePanel1.add(ordersTable.getTableHeader(), BorderLayout.CENTER);
        tablePanel1.add(ordersTable, BorderLayout.SOUTH);
        tablePanel1.setBackground(Color.PINK);

        DefaultTableModel inventoryDisplay = new DefaultTableModel();
        JTable inventoryTable = new JTable(inventoryDisplay);
        inventoryDisplay.addColumn("Item Number");
        inventoryDisplay.addColumn("Model Name");
        inventoryDisplay.addColumn("Color");
        inventoryDisplay.addColumn("Price");
        inventoryDisplay.addColumn("Size");
        inventoryDisplay.addColumn("Quantity");
        ArrayList<Inventory> inventory = connector.getInventory();
        Object[] rows1 = new Object[6];
        for(int i = 0; i<inventory.size(); i++){
            rows1[0] = inventory.get(i).getItemNumber();
            rows1[1] = inventory.get(i).getModelName();
            rows1[2] = inventory.get(i).getColor();
            rows1[3] = inventory.get(i).getPrice();
            rows1[4] = inventory.get(i).getSize();
            rows1[5] = inventory.get(i).getQuantity();
            inventoryDisplay.addRow(rows1);
        }
        inventoryTable.setShowGrid(true);
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        //table name
        JLabel inventoryTitle = new JLabel("Inventory:");

        //organizing panels
        tablePanel2.add(inventoryTitle, BorderLayout.NORTH);
        tablePanel2.add(inventoryTable.getTableHeader(), BorderLayout.CENTER);
        tablePanel2.add(inventoryTable, BorderLayout.SOUTH);
        tablePanel2.setBackground(Color.PINK);

        //make sure the frame height fits
        frameHeight = 230 + (orders.size() * 15) + (inventory.size() * 15);

        //organizing panels
        inputPanel.add(backButton, SwingConstants.CENTER);
        inputPanel.add(refresh, SwingConstants.CENTER);
        inputPanel.add(confirmOrder, SwingConstants.CENTER);
        inputPanel.add(panel4, SwingConstants.CENTER);
        inputPanel.add(panel3, SwingConstants.CENTER);
        inputPanel.add(panel2, SwingConstants.CENTER);
        inputPanel.add(panel1, SwingConstants.CENTER);
        inputPanel.setBackground(Color.PINK);

        tablesPanel.add(tablePanel1, BorderLayout.NORTH);
        tablesPanel.add(tablePanel2, BorderLayout.CENTER);

        //add to frame
        c.add(titleLabel, BorderLayout.NORTH);
        c.add(inputPanel, BorderLayout.CENTER);
        c.add(tablesPanel, BorderLayout.SOUTH);

        //set frame size
        frame.setSize(new Dimension(600, frameHeight));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Back to Menu")){
            //goes back to menu
            CustomerMenu cM = new CustomerMenu();
            cM.setVisible(true);
            this.setVisible(false);
        }
        if(e.getActionCommand().equals("Refresh")){
            //updates the frame
            MakeOrder mO = new MakeOrder();
            mO.setVisible(true);
            this.setVisible(false);
        }
    }
}
