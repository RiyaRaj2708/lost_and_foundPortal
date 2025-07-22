package ViewLost;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewLostItemsWithImage extends JFrame {

    DefaultTableModel model;
    JTable table;
    JTextField searchField;

    public ViewLostItemsWithImage() {
        setTitle("View Lost Items with Image");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top panel for search
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("🔍 Search: ");
        searchField = new JTextField();
        topPanel.add(searchLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"ID", "Name", "Item", "Description", "Place", "Date", "Contact", "Image"};
        model = new DefaultTableModel(null, columns) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 7 ? ImageIcon.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table non-editable
            }
        };
        table = new JTable(model);
        table.setRowHeight(100);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backBtn = new JButton("⬅ Back");
        backBtn.setBackground(new Color(220, 53, 69));
        backBtn.setForeground(Color.WHITE);
        add(backBtn, BorderLayout.SOUTH);
        backBtn.addActionListener(e -> dispose());

        // Search filter logic
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });

        loadData();
        setVisible(true);
    }

    private void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/lost_and_found", "root", "riyaraj@1234");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM lost_items");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("Your_Name");
                String item = rs.getString("Item_Name");
                String desc = rs.getString("Description");
                String place = rs.getString("Place");
                String date = rs.getString("Date");
                String contact = rs.getString("Contact");
                String imagePath = rs.getString("image_path");

                ImageIcon icon = null;
                if (imagePath != null && !imagePath.isEmpty()) {
                    ImageIcon raw = new ImageIcon(imagePath);
                    Image scaled = raw.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(scaled);
                }

                model.addRow(new Object[]{id, name, item, desc, place, date, contact, icon});
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterTable() {
        String text = searchField.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 2, 4)); // filters Name, Item, Place
    }

    public static void main(String[] args) {
        new ViewLostItemsWithImage();
    }
}
