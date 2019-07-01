package com.finalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewInventory extends JFrame implements ActionListener {
    //connects to database
    MySQLConnector connector = new MySQLConnector("mysql://localhost/FinalProject?serverTimezone=GMT", "root", "gadiza");

    //initializing variable
    private int frameHeight;

    public ViewInventory(){
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
        JPanel buttonPanel = new JPanel(new FlowLayout());

        //label for heading
        JLabel titleLabel = new JLabel("Items in Store", SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.BLACK);

        //button to go back to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 30));
        backButton.addActionListener(this);

        buttonPanel.add(backButton, SwingConstants.CENTER);
        buttonPanel.setBackground(Color.PINK);

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
        frameHeight = 105 + (inventory.size() * 15);

        //organizing panels
        tablePanel.add(inventoryTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(inventoryTable, BorderLayout.CENTER);
        tablePanel.setBackground(Color.PINK);

        //add to frame
        c.add(titleLabel, BorderLayout.NORTH);
        c.add(buttonPanel, BorderLayout.CENTER);
        c.add(tablePanel, BorderLayout.SOUTH);

        //size of frame
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
    }
}
