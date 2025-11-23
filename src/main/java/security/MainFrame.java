package security;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class MainFrame extends JFrame {
    private JPanel headerPanel;
    private JLabel iconLabel;
    private JLabel titleLabel;

    private JTabbedPane tabbedPane;
    private EncryptionPanel encryptionPanel;
    private DecryptionPanel decryptionPanel;

    public MainFrame() {
        super("Feistel Cipher Application");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setBackground(Color.decode("#2B2B2B"));
        
        // Set tab selection colors
        UIManager.put("TabbedPane.selected", Color.decode("#3C3F41"));
        UIManager.put("TabbedPane.focus", Color.decode("#4A90E2"));
        UIManager.put("TabbedPane.selectHighlight", Color.decode("#4A90E2"));

        headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        tabbedPane = createTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        header.setBackground(Color.decode("#2B2B2B"));

        iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/Logo.png"));
        iconLabel.setIcon(icon);
        header.add(iconLabel);

        titleLabel = new JLabel("Feistel Cipher Application");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);

        return header;
    }

    private JTabbedPane createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabbedPane.setBackground(Color.decode("#2B2B2B"));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setOpaque(true);

        encryptionPanel = new EncryptionPanel();
        decryptionPanel = new DecryptionPanel();

        tabbedPane.addTab("Encryption", encryptionPanel);
        tabbedPane.addTab("Decryption", decryptionPanel);
        
        // Set the background of the tab area
        //tabbedPane.setBackgroundAt(0, Color.decode("#2B2B2B"));
        //tabbedPane.setBackgroundAt(1, Color.decode("#2B2B2B"));

        return tabbedPane;
    }
}