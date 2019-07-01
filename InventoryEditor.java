package com.finalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryEditor extends JFrame implements ActionListener {
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

    public InventoryEditor(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inventory Editor");

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
        JLabel titleLabel = new JLabel("Edit Inventory", SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.BLACK);

        //all the input labels and input textfields, added to different panels to keep it organized
        JLabel itemNo = new JLabel("Item Number to Be Updated: ");
        itemNo.setOpaque(false);
        itemNo.setForeground(Color.BLACK);

        JTextField inputNo = new JTextField(10);

        panel1.add(itemNo);
        panel1.add(inputNo);
        panel1.setBackground(Color.PINK);

        JLabel itemModel = new JLabel("New Model Name: ");
        itemModel.setOpaque(false);
        itemModel.setForeground(Color.BLACK);

        JTextField inputName = new JTextField(10);

        panel2.add(itemModel);
        panel2.add(inputName);
        panel2.setBackground(Color.PINK);

        JLabel itemColor = new JLabel("New Color: ");
        itemColor.setOpaque(false);
        itemColor.setForeground(Color.BLACK);

        JTextField inputColor = new JTextField(10);

        panel3.add(itemColor);
        panel3.add(inputColor);
        panel3.setBackground(Color.PINK);

        JLabel itemPrice = new JLabel("New Price: ");
        itemPrice.setOpaque(false);
        itemPrice.setForeground(Color.BLACK);

        JTextField inputPrice = new JTextField(10);

        panel4.add(itemPrice);
        panel4.add(inputPrice);
        panel4.setBackground(Color.PINK);

        JLabel itemSize = new JLabel("New Size: ");
        itemSize.setOpaque(false);
        itemSize.setForeground(Color.BLACK);

        JTextField inputSize = new JTextField(10);

        panel5.add(itemSize);
        panel5.add(inputSize);
        panel5.setBackground(Color.PINK);

        JLabel itemQty = new JLabel("New Quantity: ");
        itemQty.setOpaque(false);
        itemQty.setForeground(Color.BLACK);

        JTextField inputQty = new JTextField(10);

        panel6.add(itemQty);
        panel6.add(inputQty);
        panel6.setBackground(Color.PINK);

        //button to update items
        JButton updateButton = new JButton("Update Item");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfields
                String editItem = e.getActionCommand();
                String itemNumberInput = inputNo.getText();
                itemNumber = Integer.parseInt(itemNumberInput);
                modelName = inputName.getText();
                color = inputColor.getText();
                String itemPriceInput = inputPrice.getText();
                price = Float.parseFloat(itemPriceInput);
                size = inputSize.getText();
                String qtyInput = inputQty.getText();
                quantity = Integer.parseInt(qtyInput);

                //validates inputs
                try{
                    Integer.parseInt(itemNumberInput);
                    checkDataType1 = true;
                }
                catch(NumberFormatException n){
                    checkDataType1 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }
                try{
                    Float.parseFloat(itemPriceInput);
                    checkDataType2 = true;
                }
                catch(NumberFormatException n){
                    checkDataType2 = false;
                    JOptionPane.showMessageDialog(null, "Please input Float");
                }
                try{
                    Integer.parseInt(qtyInput);
                    checkDataType3 = true;
                }
                catch(NumberFormatException n){
                    checkDataType3 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }

                if(editItem.equals("Update Item")){
                    //query for MySQL
                    ResultSet rs = connector.execQuery(String.format("SELECT itemNumber FROM inventory WHERE itemNumber = '%d'", itemNumber));

                    try{
                        if(rs.next()){
                            //checking if item number exists
                            checkInventory = true;
                        }
                        else{
                            checkInventory = false;
                            JOptionPane.showMessageDialog(null, "The item number does not exist!");
                        }
                    }
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }

                    //only inputs if all the validations are checked
                    if(checkDataType1 && checkDataType2 && checkDataType3 && checkInventory){
                        //query for MySQL
                        connector.execUpdate(String.format("UPDATE inventory SET modelName = '%s', color = '%s', price = '%f', size = '%s', quantity = '%d' WHERE itemNumber = '%d'", modelName, color, price, size, quantity, itemNumber));
                        JOptionPane.showMessageDialog(null, "Item Successfully Updated!");
                    }
                }

            }
        });

        //button to delete items
        JButton deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfield
                String deleteItem = e.getActionCommand();
                String itemNumberInput = inputNo.getText();

                //validates input
                try{
                    itemNumber = Integer.parseInt(itemNumberInput);
                    checkDataType1 = true;
                }
                catch(NumberFormatException n){
                    checkDataType1 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }

                if(deleteItem.equals("Delete Item")){
                    ResultSet rs = connector.execQuery(String.format("SELECT itemNumber FROM inventory WHERE itemNumber = '%d'", itemNumber));
                    //query for MySQL
                    try{
                        if(rs.next()){
                            //checking if item number exists
                            checkInventory = true;
                        }
                        else{
                            checkInventory = false;
                            JOptionPane.showMessageDialog(null, "The item number does not exist!");
                        }
                    }
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }

                    //only inputs if all the validations are checked
                    if(checkDataType1 && checkInventory){
                        //query for MySQL
                        connector.execUpdate(String.format("DELETE FROM inventory WHERE itemNumber = '%d'", itemNumber));
                        JOptionPane.showMessageDialog(null, "Item Successfully Deleted!");

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
        frameHeight = 230+(inventory.size() * 15);

        tablePanel.add(inventoryTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(inventoryTable, BorderLayout.CENTER);
        tablePanel.setBackground(Color.PINK);

        //instructions to delete
        JLabel deleteLabel = new JLabel("Input item number to delete.");
        deleteLabel.setOpaque(false);
        deleteLabel.setForeground(Color.BLACK);

        //organizing panels
        inputPanel.add(panel1);
        inputPanel.add(panel2);
        inputPanel.add(panel3);
        inputPanel.add(panel4);
        inputPanel.add(panel5);
        inputPanel.add(panel6);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(refresh);
        inputPanel.add(backButton);
        inputPanel.add(deleteLabel);
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
            InventoryEditor iENew = new InventoryEditor();
            iENew.setVisible(true);
            this.setVisible(false);
        }
    }
}
