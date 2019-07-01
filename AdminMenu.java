package com.finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame{
    //using JFrame to create GUI

    public AdminMenu(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Welcome Admin!");

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        Dimension buttonSize = new Dimension(150, 50);

        //setting the frame
        Container c = getContentPane(); //ensures that there is only one content pane
        c.setBackground(Color.PINK);
        c.setLayout(new FlowLayout());
        frame.setSize(new Dimension(200, 360));

        //add to inventory button + attributes
        JButton addToInventory = new JButton("Add to Inventory");
        addToInventory.setForeground(Color.BLACK);
        addToInventory.setPreferredSize(buttonSize);
        addToInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = e.getActionCommand();
                if(a.equals("Add to Inventory")){
                    //moves to add inventory when clicked
                    AddInventory aTI = new AddInventory();
                    aTI.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });

        //edit to inventory button + attributes
        JButton editInventory = new JButton("Edit Inventory");
        editInventory.setForeground(Color.BLACK);
        editInventory.setPreferredSize(buttonSize);
        editInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = e.getActionCommand();
                if(a.equals("Edit Inventory")){
                    //moves to edit inventory when clicked
                    InventoryEditor iE = new InventoryEditor();
                    iE.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });

        //add customers button + attributes
        JButton addCustomers = new JButton("Add Customers");
        addCustomers.setForeground(Color.BLACK);
        addCustomers.setPreferredSize(buttonSize);
        addCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a = e.getActionCommand();
                if(a.equals("Add Customers")){
                    //moves to add customers when clicked
                    AddCustomers aC = new AddCustomers();
                    aC.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });

        //edit customers button + attributes
        JButton editCustomers = new JButton("Edit Customers");
        editCustomers.setForeground(Color.BLACK);
        editCustomers.setPreferredSize(buttonSize);
        editCustomers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //moves to edit customers when clicked
                CustomersEditor cE = new CustomersEditor();
                cE.setVisible(true);
                frame.setVisible(false);
            }
        });

        //edit orders button + attributes
        JButton orders = new JButton("View Orders");
        orders.setForeground(Color.BLACK);
        orders.setPreferredSize(buttonSize);
        orders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //moves to edit orders when clicked
                OrdersEditor oE = new OrdersEditor();
                oE.setVisible(true);
                frame.setVisible(false);
            }
        });

        //back button + attribute
        JButton back = new JButton("Back");
        back.setForeground(Color.BLACK);
        back.setPreferredSize(buttonSize);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //goes back to main menu when clicked
                MainMenu m = new MainMenu();
                m.setVisible(true);
                frame.setVisible(false);
            }
        });

        //adding to frame
        c.add(addToInventory, SwingConstants.CENTER);
        c.add(editInventory, SwingConstants.CENTER);
        c.add(addCustomers, SwingConstants.CENTER);
        c.add(editCustomers, SwingConstants.CENTER);
        c.add(orders, SwingConstants.CENTER);
        c.add(back, SwingConstants.CENTER);
    }

}
