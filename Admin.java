package com.finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin extends JFrame {
    //using JFrame to create GUI
    //setting username and password
    String username = "user123";
    String password = "password";

    public Admin(){
        JFrame frame = this; //initializing frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes when you press the exit button
        frame.setTitle("Admin"); //setting title of frame

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        Dimension buttonSize = new Dimension(90, 50); //button size

        //setting the frame
        Container c = getContentPane(); //ensures that there is only one content pane
        c.setBackground(Color.PINK);
        c.setLayout(new BorderLayout());
        frame.setSize(new Dimension(300, 150));

        //panels to organize frame
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        panel1.setBackground(Color.PINK);
        panel2.setBackground(Color.PINK);
        panel3.setBackground(Color.PINK);

        //label for input
        JLabel userName = new JLabel("Username: ");
        userName.setOpaque(false);
        userName.setForeground(Color.BLACK);

        //textfield for input
        JTextField inputUser = new JTextField(10);

        //label for input
        JLabel passWord = new JLabel("Password: ");
        passWord.setOpaque(false);
        passWord.setForeground(Color.BLACK);

        //textfield for input
        JTextField inputPass = new JTextField(10);

        //submit button + attributes
        JButton submit = new JButton("Submit");
        submit.setForeground(Color.BLACK);
        submit.setPreferredSize(buttonSize);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets the content of the textfields
                String user = inputUser.getText();
                String pass = inputPass.getText();
                String s = e.getActionCommand();

                if(s.equals("Submit")){
                    //validates username and password
                    if(user.equals(username) && pass.equals(password)){
                        //if correct, moves to admin menu
                        AdminMenu aM = new AdminMenu();
                        aM.setVisible(true);
                        frame.setVisible(false);
                    }
                    else{
                        //returns to main menu if incorrect
                        JOptionPane.showMessageDialog(null, "Please make sure the username and password is correct!");
                        MainMenu m = new MainMenu();
                        m.setVisible(true);
                        frame.setVisible(false);
                    }
                }
            }
        });

        //organizing layout frame
        panel1.add(userName, SwingConstants.CENTER);
        panel1.add(inputUser);
        panel2.add(passWord, SwingConstants.CENTER);
        panel2.add(inputPass);
        panel3.add(submit, SwingConstants.CENTER);

        //adding to frame
        c.add(panel1, BorderLayout.NORTH);
        c.add(panel2, BorderLayout.CENTER);
        c.add(panel3, BorderLayout.SOUTH);

    }

}
