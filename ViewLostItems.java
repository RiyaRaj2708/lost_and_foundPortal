package ViewLost;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewLostItems extends JFrame {

    public ViewLostItems() {
        setTitle("View Lost Items");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Columns for the table
        String[] columns = {"ID", "Your Name", "Item Name", "Description", "Place", "Date", "Contact", "Image Path"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backBtn = new JButton("Back");
        add(backBtn, BorderLayout.SOUTH);
        backBtn.addActionListener(e -> dispose());

        // Fetch Data from Database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/lost_and_found",
                    "root",
                    "riyaraj@1234"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM lost_items");

            while (rs.next()) {
                int id = rs.getInt("id");
                String yourName = rs.getString("Your_Name");
                String itemName = rs.getString("Item_Name");
                String desc = rs.getString("Description");
                String place = rs.getString("Place");
                String date = rs.getString("Date");
                String contact = rs.getString("Contact");
                String imagePath = rs.getString("image_path");

                model.addRow(new Object[]{id, yourName, itemName, desc, place, date, contact, imagePath});
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewLostItems();
    }
}
