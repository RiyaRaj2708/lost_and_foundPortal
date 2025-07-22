package com.riya.lostfound;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignupForm extends JFrame {

    public SignupForm() {
        setTitle("User Signup");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // 📝 Form Fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton signupBtn = new JButton("Signup");
        JButton backBtn = new JButton("Back");

        // 🔁 Add to layout
        add(usernameLabel); add(usernameField);
        add(passwordLabel); add(passwordField);
        add(emailLabel); add(emailField);
        add(signupBtn); add(backBtn);

        // 🎯 Signup Button Action
        signupBtn.addActionListener(e -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/lost_and_found", "root", "riyaraj@1234"
                );

                String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, usernameField.getText());
                ps.setString(2, String.valueOf(passwordField.getPassword()));
                ps.setString(3, emailField.getText());

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Signup Successful! Please login.");
                    dispose(); // Close signup
                }

                con.close();
            } catch (SQLIntegrityConstraintViolationException dup) {
                JOptionPane.showMessageDialog(this, "❌ Username already exists!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        // 🔙 Back button
        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        new SignupForm();
    }
}
