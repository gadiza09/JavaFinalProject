package com.finalProject;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;


public class MainMenu extends JFrame implements ActionListener{
//using JFrame to create GUI and ActionListener to accept commands from objects

    public MainMenu(){
        JFrame frame = this; //initializing frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes when you press the exit button
        frame.setTitle("Main Menu"); //setting title of frame

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        Container c = getContentPane(); //ensures that there is only one content pane
        c.setLayout(new BorderLayout()); //sets layout
        c.setBackground(Color.BLACK); //sets background color

        Dimension buttonSize = new Dimension(90, 50); //button size

        //panels to organize frame
        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        panel1.setBackground(Color.BLACK);
        panel2.setBackground(Color.BLACK);

        //label for heading of frame
        JLabel label1 = new JLabel("Welcome to my store!", SwingConstants.CENTER);
        label1.setForeground(Color.PINK);
        label1.setOpaque(false);
        //setting where the label will be
        panel1.add(label1, BorderLayout.NORTH);

        //a button and all its attributes
        JButton adminButton = new JButton("ADMIN");
        adminButton.setBackground(Color.BLACK);
        adminButton.setForeground(Color.PINK);
        adminButton.setPreferredSize(buttonSize);
        adminButton.setOpaque(true);
        //so it can accept commands
        adminButton.addActionListener(this);
        //setting where the button will be
        panel2.add(adminButton, BorderLayout.CENTER);

        //a button and all its attributes
        JButton otherButton = new JButton("CUSTOMER");
        otherButton.setBackground(Color.BLACK);
        otherButton.setForeground(Color.PINK);
        otherButton.setPreferredSize(buttonSize);
        otherButton.setOpaque(true);
        //so it can accept commands
        otherButton.addActionListener(this);
        //setting where the button will be
        panel2.add(otherButton, BorderLayout.CENTER);

        //organizing the contents of the frame and its size
        c.add(panel1, BorderLayout.NORTH);
        c.add(panel2, BorderLayout.CENTER);
        frame.setSize(new Dimension(300, 100));

    }

    //from ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        //user chooses admin
        if(e.getActionCommand().equals("ADMIN")){
            Admin a = new Admin();
            this.setVisible(false);

        }
        //user chooses customer
        if(e.getActionCommand().equals("CUSTOMER")){
            CustomerMenu c = new CustomerMenu();
            this.setVisible(false);

        }
    }
}
