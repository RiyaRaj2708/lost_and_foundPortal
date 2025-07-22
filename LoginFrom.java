import javax.swing.*;
import java.awt.*;
import LostItemForm.LostItemForm;
import FoundItemForm.FoundItemForm;
import ViewLost.ViewLostItemsWithImage;
import ViewFound.ViewFoundItemsWithImage;

public class MainMenu extends JFrame {

    public MainMenu(String username) {
        setTitle("Lost & Found Portal - Campus");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        // 👋 Welcome message
        JLabel welcomeLabel = new JLabel("👋 Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(0, 102, 204));
        add(welcomeLabel);

        // Buttons
        JButton lostButton = new JButton("Report Lost Item");
        JButton foundButton = new JButton("Report Found Item");
        JButton viewLostButton = new JButton("View Lost Items");
        JButton viewLostWithImageButton = new JButton("View Lost Items (with Image)");
        JButton viewFoundButton = new JButton("View Found Items");
        JButton viewFoundWithImageButton = new JButton("View Found Items (with Image)");
        JButton exitButton = new JButton("Exit");

        // Add Buttons
        add(lostButton);
        add(foundButton);
        add(viewLostButton);
        add(viewLostWithImageButton);
        add(viewFoundButton);
        add(viewFoundWithImageButton);
        add(exitButton);

        // 🧑‍🎨 Button styling
        JButton[] buttons = {
                lostButton, foundButton, viewLostButton, viewLostWithImageButton,
                viewFoundButton, viewFoundWithImageButton, exitButton
        };

        for (JButton btn : buttons) {
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
        }

        // 🔘 Actions
        lostButton.addActionListener(e -> new LostItemForm());
        foundButton.addActionListener(e -> new FoundItemForm());
        viewLostButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "👀 View Lost Items coming soon..."));
        viewLostWithImageButton.addActionListener(e -> new ViewLostItemsWithImage());
        viewFoundButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "👀 View Found Items coming soon..."));
        viewFoundWithImageButton.addActionListener(e -> new ViewFoundItemsWithImage());
        exitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // For testing only
    public static void main(String[] args) {
        new MainMenu("TestUser");
    }
}
