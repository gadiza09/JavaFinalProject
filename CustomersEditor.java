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

public class CustomersEditor extends JFrame implements ActionListener {
    //connects to database
    MySQLConnector connector = new MySQLConnector("mysql://localhost/FinalProject?serverTimezone=GMT", "root", "gadiza");

    //initializing variables
    private int customerNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private String username;
    private String password;
    private int frameHeight;
    private boolean checkDataType1;
    private boolean checkDataType2;
    private boolean checkCustomer;
    String dOB;

    public CustomersEditor(){
        //initializing frame
        JFrame frame = this;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Customers Editor");

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
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel panel8 = new JPanel(new FlowLayout());

        //label for heading
        JLabel titleLabel = new JLabel("Edit Customers", SwingConstants.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setForeground(Color.BLACK);

        //all the input labels and input textfields, added to different panels to keep it organized
        JLabel custNo = new JLabel("Customer Number to Be Updated: ");
        custNo.setOpaque(false);
        custNo.setForeground(Color.BLACK);

        JTextField inputNo = new JTextField(10);

        panel1.add(custNo);
        panel1.add(inputNo);
        panel1.setBackground(Color.PINK);

        JLabel custName = new JLabel("New Name: ");
        custName.setOpaque(false);
        custName.setForeground(Color.BLACK);

        JTextField inputName = new JTextField(10);

        panel2.add(custName);
        panel2.add(inputName);
        panel2.setBackground(Color.PINK);

        JLabel custEmail = new JLabel("New Email: ");
        custEmail.setOpaque(false);
        custEmail.setForeground(Color.BLACK);

        JTextField inputEmail = new JTextField(10);

        panel3.add(custEmail);
        panel3.add(inputEmail);
        panel3.setBackground(Color.PINK);

        JLabel custPHNo = new JLabel("New Phone Number: ");
        custPHNo.setOpaque(false);
        custPHNo.setForeground(Color.BLACK);

        JTextField inputPHNo = new JTextField(10);

        panel4.add(custPHNo);
        panel4.add(inputPHNo);
        panel4.setBackground(Color.PINK);

        JLabel custAddress = new JLabel("New Address: ");
        custAddress.setOpaque(false);
        custAddress.setForeground(Color.BLACK);

        JTextField inputAddress = new JTextField(10);

        panel5.add(custAddress);
        panel5.add(inputAddress);
        panel5.setBackground(Color.PINK);

        JLabel custDOB = new JLabel("New Date of Birth (YYYY-MM-DD): ");
        custDOB.setOpaque(false);
        custDOB.setForeground(Color.BLACK);

        JTextField inputDOB = new JTextField(10);

        panel6.add(custDOB);
        panel6.add(inputDOB);
        panel6.setBackground(Color.PINK);

        JLabel custUser = new JLabel("New Username: ");
        custUser.setOpaque(false);
        custUser.setForeground(Color.BLACK);

        JTextField inputUser = new JTextField(10);

        panel7.add(custUser);
        panel7.add(inputUser);
        panel7.setBackground(Color.PINK);

        JLabel custPass = new JLabel("New Password: ");
        custPass.setOpaque(false);
        custPass.setForeground(Color.BLACK);

        JTextField inputPass = new JTextField(10);

        panel8.add(custPass);
        panel8.add(inputPass);
        panel8.setBackground(Color.PINK);

        //button to update customers
        JButton editCustomer = new JButton("Edit Customer");
        editCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfields
                String editCust = e.getActionCommand();
                String custNoInput = inputNo.getText();
                name = inputName.getText();
                email = inputEmail.getText();
                phoneNumber = inputPHNo.getText();
                address = inputAddress.getText();
                dOB = inputDOB.getText();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                username = inputUser.getText();
                password = inputPass.getText();

                //validates inputs
                try{
                    customerNumber = Integer.parseInt(custNoInput);
                    checkDataType1 = true;
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Please input Integer!");
                    checkDataType1 = false;
                }

                try{
                    dateOfBirth = dateFormat.parse(dOB);
                    checkDataType2 = true;
                }
                catch(ParseException p){
                    JOptionPane.showMessageDialog(null, "Please input date with correct format!");
                    checkDataType2 = false;
                }

                if(editCust.equals("Edit Customer")){
                    //query for MySQL
                    ResultSet rs = connector.execQuery(String.format("SELECT customerNumber FROM customers WHERE customerNumber = '%d'", customerNumber));

                    try{
                        if(rs.next()){
                            //checking if customer number exists
                            checkCustomer = true;
                        }
                        else{
                            checkCustomer = false;
                            JOptionPane.showMessageDialog(null, "The Customer Number does not exist!");
                        }
                    }
                    catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    //only inputs if all the validations are checked
                    if(checkDataType1 && checkDataType2 && checkCustomer){
                        //query for MySQL
                        connector.execUpdate(String.format("UPDATE customers SET name = '%s', email = '%s', phoneNumber = '%s', address = '%s', dateOfBirth = '%s', username = '%s', password = '%s' WHERE customerNumber = '%d'", name, email, phoneNumber, address, dOB, username, password, customerNumber));
                        JOptionPane.showMessageDialog(null, "Customer Successfully Updated!");
                    }
                }
            }
        });

        //button to delete customers
        JButton delete = new JButton("Delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets contents of textfield
                String deleteCust = e.getActionCommand();
                String custNoInput = inputNo.getText();

                //validates input
                try{
                    customerNumber = Integer.parseInt(custNoInput);
                    checkDataType1 = true;
                }
                catch(NumberFormatException n){
                    checkDataType1 = false;
                    JOptionPane.showMessageDialog(null, "Please input Integer");
                }

                if(deleteCust.equals("Delete")){
                    //query for MySQL
                    ResultSet rs = connector.execQuery(String.format("SELECT customerNumber FROM customers WHERE customerNumber = '%d'", customerNumber));

                    try{
                        if(rs.next()){
                            //checking if customer number exists
                            checkCustomer = true;
                        }
                        else{
                            checkCustomer = false;
                            JOptionPane.showMessageDialog(null, "The Customer Number does not exist!");
                        }
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                    }

                    if(checkDataType1 && checkCustomer){
                        //query for MySQL
                        connector.execUpdate(String.format("DELETE FROM customers WHERE customerNumber = '%d'", customerNumber));
                        JOptionPane.showMessageDialog(null, "Customer Successfully Deleted!");
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
        DefaultTableModel customerDisplay = new DefaultTableModel();
        JTable customerTable = new JTable(customerDisplay);
        //column names
        customerDisplay.addColumn("Customer Number");
        customerDisplay.addColumn("Name");
        customerDisplay.addColumn("Email");
        customerDisplay.addColumn("Phone Number");
        customerDisplay.addColumn("Address");
        customerDisplay.addColumn("Date of Birth");
        customerDisplay.addColumn("Username");
        customerDisplay.addColumn("Password");
        ArrayList<Customers> customers = connector.getCustomers();
        //shows all rows
        Object[] rows = new Object[8];
        for(int i = 0; i<customers.size(); i++){
            rows[0] = customers.get(i).getCustomerNumber();
            rows[1] = customers.get(i).getName();
            rows[2] = customers.get(i).getEmail();
            rows[3] = customers.get(i).getPhoneNumber();
            rows[4] = customers.get(i).getAddress();
            rows[5] = customers.get(i).getDateOfBirth();
            rows[6] = customers.get(i).getUsername();
            rows[7] = customers.get(i).getPassword();
            customerDisplay.addRow(rows);
        }
        customerTable.setShowGrid(true);
        //make sure the frame height fits
        frameHeight = 250 + (customers.size() * 15);

        //organizing panels
        tablePanel.add(customerTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(customerTable, BorderLayout.CENTER);
        tablePanel.setBackground(Color.PINK);

        //instructions to delete
        JLabel deleteLabel = new JLabel("Input customer number to delete.");
        deleteLabel.setOpaque(false);
        deleteLabel.setForeground(Color.BLACK);

        inputPanel.setBackground(Color.PINK);
        inputPanel.add(panel8, SwingConstants.CENTER);
        inputPanel.add(panel7, SwingConstants.CENTER);
        inputPanel.add(panel6, SwingConstants.CENTER);
        inputPanel.add(panel5, SwingConstants.CENTER);
        inputPanel.add(panel4, SwingConstants.CENTER);
        inputPanel.add(panel3, SwingConstants.CENTER);
        inputPanel.add(panel2, SwingConstants.CENTER);
        inputPanel.add(panel1, SwingConstants.CENTER);
        inputPanel.add(editCustomer);
        inputPanel.add(delete);
        inputPanel.add(refresh);
        inputPanel.add(backButton);
        inputPanel.add(deleteLabel);

        //add to frame
        c.add(titleLabel, BorderLayout.NORTH);
        c.add(inputPanel, BorderLayout.CENTER);
        c.add(tablePanel, BorderLayout.SOUTH);

        //size of frame
        frame.setSize(new Dimension(800, frameHeight));
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
            CustomersEditor cENew = new CustomersEditor();
            cENew.setVisible(true);
            this.setVisible(false);
        }
    }
}
