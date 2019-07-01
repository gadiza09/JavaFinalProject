package com.finalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersEditor extends JFrame implements ActionListener{
    //connects to database
    MySQLConnector connector = new MySQLConnector("mysql://localhost/FinalProject?serverTimezone=GMT", "root", "gadiza");

    //initializing variables
    private int orderNumber;
    private String status;
    private boolean checkDataType = false;
    private boolean checkOrders = false;
    private int frameHeight;

    public OrdersEditor(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Orders Editor");

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

        //label for heading
        JLabel titleLabel = new JLabel("Edit Orders", SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.BLACK);

        //all the input labels and input textfields, added to different panels to keep it organized
        JLabel orderNo = new JLabel("Order Number: ");
        orderNo.setOpaque(false);
        orderNo.setForeground(Color.BLACK);

        JTextField inputNo = new JTextField(10);

        panel1.add(inputNo, SwingConstants.CENTER);
        panel1.add(orderNo, SwingConstants.CENTER);
        panel1.setBackground(Color.PINK);

        JLabel orderStatus = new JLabel("Status: ");
        orderStatus.setOpaque(false);
        orderStatus.setForeground(Color.BLACK);

        JTextField inputStatus = new JTextField(10);

        panel2.add(inputStatus, SwingConstants.CENTER);
        panel2.add(orderStatus, SwingConstants.CENTER);
        panel2.setBackground(Color.PINK);

        //button to update order
        JButton confirmStatus = new JButton("Confirm Status");
        confirmStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfields
                String confirm = e.getActionCommand();
                String theOrder = inputNo.getText();
                status = inputStatus.getText();

                //validates inputs
                try{
                    orderNumber = Integer.parseInt(theOrder);
                    checkDataType = true;
                }
                catch(NumberFormatException n){
                    checkDataType = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer!");
                }

                if(confirm.equals("Confirm Status")){
                    //query for MySQL
                    ResultSet rs = connector.execQuery(String.format("SELECT orderNumber FROM orders WHERE orderNumber = '%d'", orderNumber));

                    try{
                        if(rs.next()){
                            //checking if order number exists
                            checkOrders = true;
                        }
                        else{
                            checkOrders = false;
                            JOptionPane.showMessageDialog(null, "The Order Number does not exist!");
                        }
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                    }
                    //only inputs if all the validations are checked
                    if(checkDataType && checkOrders){
                        //query for MySQL
                        connector.execUpdate(String.format("UPDATE orders SET status = '%s' WHERE orderNumber = '%d'", status, orderNumber));
                        JOptionPane.showMessageDialog(null, "Status has been updated!");
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
        //make sure the frame height fits
        frameHeight = 130 + (orders.size() * 15);

        //organizing panels
        tablePanel.add(ordersTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(ordersTable, BorderLayout.CENTER);
        tablePanel.setBackground(Color.PINK);

        inputPanel.add(backButton, SwingConstants.CENTER);
        inputPanel.add(refresh, SwingConstants.CENTER);
        inputPanel.add(confirmStatus, SwingConstants.CENTER);
        inputPanel.add(panel2, SwingConstants.CENTER);
        inputPanel.add(panel1, SwingConstants.CENTER);
        inputPanel.setBackground(Color.PINK);

        //add to frame
        c.add(titleLabel, BorderLayout.NORTH);
        c.add(inputPanel, BorderLayout.CENTER);
        c.add(tablePanel, BorderLayout.SOUTH);

        //size of frame
        frame.setSize(new Dimension(500, frameHeight));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Back to Menu")){
            AdminMenu am = new AdminMenu();
            am.setVisible(true);
            this.setVisible(false);
        }
        else if(e.getActionCommand().equals("Refresh")){
            OrdersEditor oENew = new OrdersEditor();
            oENew.setVisible(true);
            this.setVisible(false);
        }
    }
}
