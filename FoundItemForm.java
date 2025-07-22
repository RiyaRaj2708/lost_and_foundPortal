package FoundItemForm;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class FoundItemForm extends JFrame {

    private File selectedImageFile = null;

    public FoundItemForm() {
        setTitle("Report Found Item");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

        JLabel nameLabel = new JLabel("Your Name:");
        JTextField nameField = new JTextField();

        JLabel itemLabel = new JLabel("Item Name:");
        JTextField itemField = new JTextField();

        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();

        JLabel placeLabel = new JLabel("Found Place:");
        JTextField placeField = new JTextField();

        JLabel dateLabel = new JLabel("Date (DD-MM-YYYY):");
        JTextField dateField = new JTextField();

        JLabel contactLabel = new JLabel("Contact:");
        JTextField contactField = new JTextField();

        JButton chooseImgBtn = new JButton("Choose Image (optional)");
        JLabel imgLabel = new JLabel("No image selected");

        JButton submitBtn = new JButton("Submit");
        JButton backBtn = new JButton("Back");

        add(nameLabel); add(nameField);
        add(itemLabel); add(itemField);
        add(descLabel); add(descField);
        add(placeLabel); add(placeField);
        add(dateLabel); add(dateField);
        add(contactLabel); add(contactField);
        add(chooseImgBtn); add(imgLabel);
        add(submitBtn); add(backBtn);

        // 🖼️ Image selection
        chooseImgBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                imgLabel.setText(selectedImageFile.getName());
            }
        });

        // Submit button logic
        submitBtn.addActionListener(e -> {
            try {
                String imagePathInDb = "";

                if (selectedImageFile != null) {
                    String destDir = "images";
                    Files.createDirectories(Paths.get(destDir));
                    String destPath = destDir + "/" + System.currentTimeMillis() + "_" + selectedImageFile.getName();
                    Files.copy(selectedImageFile.toPath(), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
                    imagePathInDb = destPath;
                }

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/lost_and_found",
                        "root",
                        "riyaraj@1234"
                );

                String sql = "INSERT INTO found_items (Your_Name, Item_Name, Description, Place, Date, Contact, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameField.getText());
                ps.setString(2, itemField.getText());
                ps.setString(3, descField.getText());
                ps.setString(4, placeField.getText());
                ps.setString(5, dateField.getText());
                ps.setString(6, contactField.getText());
                ps.setString(7, imagePathInDb);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Found item reported!");
                    nameField.setText("");
                    itemField.setText("");
                    descField.setText("");
                    placeField.setText("");
                    dateField.setText("");
                    contactField.setText("");
                    selectedImageFile = null;
                    imgLabel.setText("No image selected");
                }

                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        new FoundItemForm();
    }
}
