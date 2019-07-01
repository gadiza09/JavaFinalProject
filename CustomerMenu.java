package com.finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenu extends JFrame {

    public CustomerMenu(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Welcome Customer!");

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        Dimension buttonSize = new Dimension(150, 50);

        //setting the frame
        Container c = getContentPane(); //ensures that there is only one content pane
        c.setBackground(Color.PINK);
        c.setLayout(new FlowLayout());
        frame.setSize(new Dimension(200, 250));

        //view inventory button + attributes
        JButton viewInventory = new JButton("View Inventory");
        viewInventory.setForeground(Color.BLACK);
        viewInventory.setPreferredSize(buttonSize);
        viewInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //moves to view inventory when clicked
                ViewInventory vI = new ViewInventory();
                vI.setVisible(true);
                frame.setVisible(false);

            }
        });

        //make order button + attributes
        JButton makeOrder = new JButton("Make an Order");
        makeOrder.setForeground(Color.BLACK);
        makeOrder.setPreferredSize(buttonSize);
        makeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //moves to make order when clicked
                MakeOrder mo = new MakeOrder();
                mo.setVisible(true);
                frame.setVisible(false);
            }
        });

        //new customers button + attributes
        JButton newCustomer = new JButton("New Customer");
        newCustomer.setForeground(Color.BLACK);
        newCustomer.setPreferredSize(buttonSize);
        newCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //moves to new customers when clicked
                NewCustomer nC = new NewCustomer();
                nC.setVisible(true);
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
        c.add(back);
        c.add(viewInventory);
        c.add(newCustomer);
        c.add(makeOrder);
    }
}
