package com.finalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.*;

public class AddInventory extends JFrame implements ActionListener {
    //connects to database
    MySQLConnector connector = new MySQLConnector("mysql://localhost/FinalProject?serverTimezone=GMT", "root", "gadiza");

    //initializing variables
    private int itemNumber;
    private String modelName;
    private String color;
    private float price;
    private String size;
    private int quantity;
    private boolean checkInventory = false;
    private boolean checkDataType1 = false;
    private boolean checkDataType2 = false;
    private boolean checkDataType3 = false;
    private int frameHeight;

    public AddInventory(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory");

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //setting the frame
        Container c = getContentPane(); //ensures that there is only one content pane
        c.setBackground(Color.PINK);
        c.setLayout(new BorderLayout());

        //panels to organize frame
        JPanel tablePanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());

        //label for heading
        JLabel titleLabel = new JLabel("Add to Inventory", SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.BLACK);

        //all the input labels and input textfields, added to different panels to keep it organized
        JLabel itemNo = new JLabel("Item Number: ");
        itemNo.setOpaque(false);
        itemNo.setForeground(Color.BLACK);

        JTextField inputNo = new JTextField(10);

        panel1.add(itemNo);
        panel1.add(inputNo);
        panel1.setBackground(Color.PINK);

        JLabel itemModel = new JLabel("Model Name: ");
        itemModel.setOpaque(false);
        itemModel.setForeground(Color.BLACK);

        JTextField inputName = new JTextField(10);

        panel2.add(itemModel);
        panel2.add(inputName);
        panel2.setBackground(Color.PINK);

        JLabel itemColor = new JLabel("Color: ");
        itemColor.setOpaque(false);
        itemColor.setForeground(Color.BLACK);

        JTextField inputColor = new JTextField(10);

        panel3.add(itemColor);
        panel3.add(inputColor);
        panel3.setBackground(Color.PINK);

        JLabel itemPrice = new JLabel("Price: ");
        itemPrice.setOpaque(false);
        itemPrice.setForeground(Color.BLACK);

        JTextField inputPrice = new JTextField(10);

        panel4.add(itemPrice);
        panel4.add(inputPrice);
        panel4.setBackground(Color.PINK);

        JLabel itemSize = new JLabel("Size: ");
        itemSize.setOpaque(false);
        itemSize.setForeground(Color.BLACK);

        JTextField inputSize = new JTextField(10);

        panel5.add(itemSize);
        panel5.add(inputSize);
        panel5.setBackground(Color.PINK);

        JLabel itemQty = new JLabel("Quantity: ");
        itemQty.setOpaque(false);
        itemQty.setForeground(Color.BLACK);

        JTextField inputQty = new JTextField(10);

        panel6.add(itemQty);
        panel6.add(inputQty);
        panel6.setBackground(Color.PINK);

        //button to add items
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfields
                String addItem = e.getActionCommand();
                String itemNumberInput = inputNo.getText();
                modelName = inputName.getText();
                color = inputColor.getText();
                String itemPriceInput = inputPrice.getText();
                size = inputSize.getText();
                String qtyInput = inputQty.getText();

                //validates inputs
                try{
                    itemNumber = Integer.parseInt(itemNumberInput);
                    checkDataType1 = true;
                }
                catch(NumberFormatException n){
                    checkDataType1 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }
                try{
                    price = Float.parseFloat(itemPriceInput);
                    checkDataType2 = true;
                }
                catch(NumberFormatException n){
                    checkDataType2 = false;
                    JOptionPane.showMessageDialog(null, "Please input Float");
                }
                try{
                    quantity = Integer.parseInt(qtyInput);
                    checkDataType3 = true;
                }
                catch(NumberFormatException n){
                    checkDataType3 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }

                if(addItem.equals("Add Item")){
                    //query for MySQL
                    ResultSet rs = connector.execQuery(String.format("SELECT itemNumber FROM inventory WHERE itemNumber = '%d'", itemNumber));

                    try{
                        if(rs.next()){
                            //checking if item number exists
                            checkInventory = false;
                            JOptionPane.showMessageDialog(null, "Item Number is taken!");
                        }
                        else{
                            checkInventory = true;
                        }
                    }
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    //only inputs if all the validations are checked
                    if(checkDataType1  && checkDataType2 && checkDataType3 && checkInventory){
                        //query for MySQL
                        connector.execUpdate(String.format("INSERT INTO inventory (itemNumber, modelName, color, price, size, quantity) VALUES ('%d', '%s', '%s', '%f', '%s', '%d')", itemNumber, modelName, color, price, size, quantity));
                        JOptionPane.showMessageDialog(null, "Item Successfully Added!");
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
        DefaultTableModel inventoryDisplay = new DefaultTableModel();
        JTable inventoryTable = new JTable(inventoryDisplay);
        //column names
        inventoryDisplay.addColumn("Item Number");
        inventoryDisplay.addColumn("Model Name");
        inventoryDisplay.addColumn("Color");
        inventoryDisplay.addColumn("Price");
        inventoryDisplay.addColumn("Size");
        inventoryDisplay.addColumn("Quantity");
        ArrayList<Inventory> inventory = connector.getInventory();
        //shows all rows
        Object[] rows = new Object[6];
        for(int i = 0; i<inventory.size(); i++){
            rows[0] = inventory.get(i).getItemNumber();
            rows[1] = inventory.get(i).getModelName();
            rows[2] = inventory.get(i).getColor();
            rows[3] = inventory.get(i).getPrice();
            rows[4] = inventory.get(i).getSize();
            rows[5] = inventory.get(i).getQuantity();
            inventoryDisplay.addRow(rows);
        }
        inventoryTable.setShowGrid(true);
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        //make sure the frame height fits
        frameHeight = 210+(inventory.size() * 15);

        //organizing panels
        tablePanel.add(inventoryTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(inventoryTable, BorderLayout.CENTER);
        tablePanel.setBackground(Color.PINK);

        inputPanel.add(panel1);
        inputPanel.add(panel2);
        inputPanel.add(panel3);
        inputPanel.add(panel4);
        inputPanel.add(panel5);
        inputPanel.add(panel6);
        inputPanel.add(addButton);
        inputPanel.add(refresh);
        inputPanel.add(backButton);
        inputPanel.setBackground(Color.PINK);

        //add to frame
        c.add(titleLabel, BorderLayout.NORTH);
        c.add(inputPanel, BorderLayout.CENTER);
        c.add(tablePanel, BorderLayout.SOUTH);

        //size of frame
        frame.setSize(new Dimension(600, frameHeight));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Back to Menu")){
            //goes back to menu
            AdminMenu am = new AdminMenu();
            am.setVisible(true);
            this.setVisible(false);
        }
        else if(e.getActionCommand().equals("Refresh")){
            //updates the frame
            AddInventory aiNew = new AddInventory();
            aiNew.setVisible(true);
            this.setVisible(false);
        }
    }
}
