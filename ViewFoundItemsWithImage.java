package ViewFound;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class ViewFoundItemsWithImage extends JFrame {

    public ViewFoundItemsWithImage() {
        setTitle("View Found Items with Image");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"ID", "Name", "Item", "Description", "Place", "Date", "Contact", "Image"};
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            public Class<?> getColumnClass(int column) {
                return column == 7 ? ImageIcon.class : String.class;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(100);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        add(backBtn, BorderLayout.SOUTH);
        backBtn.addActionListener(e -> dispose());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/lost_and_found",
                    "root",
                    "riyaraj@1234"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM found_items");

            while (rs.next()) {
                int id = rs.getInt("id");
                String yourName = rs.getString("Your_Name");
                String itemName = rs.getString("Item_Name");
                String desc = rs.getString("Description");
                String place = rs.getString("Place");
                String date = rs.getString("Date");
                String contact = rs.getString("Contact");
                String imagePath = rs.getString("image_path");

                ImageIcon icon = null;
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imgFile = new File(imagePath);
                    if (imgFile.exists()) {
                        ImageIcon raw = new ImageIcon(imagePath);
                        Image scaled = raw.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaled);
                    }
                }

                model.addRow(new Object[]{id, yourName, itemName, desc, place, date, contact, icon});
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewFoundItemsWithImage();
    }
}
