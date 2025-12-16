package security.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import security.model.CryptoLogic;
import security.util.TextConversion;

public class EncryptionPanel extends JPanel {
    private CryptoLogic crypto;

    private JLabel plainTextLabel;
    private JTextField plainTextField;
    private JLabel plainToHexLabel;
    private JTextField plainToHexField;

    private JLabel keysLabel;
    private JTextField firstKeyField;
    private JTextField secondKeyField;
    private JButton generateKeysButton;
    private JButton copyKeysButton;

    private JLabel cipherTextLabel;
    private JTextField cipherTextField;
    private JLabel cipherToHexLabel;
    private JTextField cipherToHexField;
    private JButton encryptButton;

    public EncryptionPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#2B2B2B"));

        crypto = new CryptoLogic();

        add(topPanel(), BorderLayout.NORTH);

        add(middlePanel(), BorderLayout.CENTER);

        add(bottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel topPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 1, 5, 5));
        topPanel.setBackground(Color.decode("#2B2B2B"));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        plainTextLabel = new JLabel("Plain Text:");
        plainTextLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        plainTextLabel.setForeground(Color.WHITE);
        topPanel.add(plainTextLabel);
        
        plainTextField = new JTextField(10);
        plainTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        topPanel.add(plainTextField);

        plainToHexLabel = new JLabel("Plain Text to Hex:");
        plainToHexLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        plainToHexLabel.setForeground(Color.WHITE);
        topPanel.add(plainToHexLabel);

        plainToHexField = new JTextField(10);
        plainToHexField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        plainToHexField.setEditable(false);
        topPanel.add(plainToHexField);

        plainTextField.getDocument().addDocumentListener(new TextFieldListener());

        return topPanel;
    }

    private JPanel middlePanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1, 5, 5));
        middlePanel.setBackground(Color.decode("#2B2B2B"));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        keysLabel = new JLabel("Encryption Keys:");
        keysLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        keysLabel.setForeground(Color.WHITE);
        middlePanel.add(keysLabel);

        firstKeyField = new JTextField(10);
        firstKeyField.setText("First Key on Hex, Ex: 00A4");
        firstKeyField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        middlePanel.add(firstKeyField);

        secondKeyField = new JTextField(10);
        secondKeyField.setText("Second Key on Hex, Ex: 003F");
        secondKeyField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        middlePanel.add(secondKeyField);

        generateKeysButton = new JButton("Generate Keys");
        generateKeysButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        generateKeysButton.setBackground(Color.decode("#3C3F41"));
        generateKeysButton.setForeground(Color.WHITE);
        middlePanel.add(generateKeysButton);

        copyKeysButton = new JButton("Copy Keys");
        copyKeysButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyKeysButton.setBackground(Color.decode("#3C3F41"));
        copyKeysButton.setForeground(Color.WHITE);
        middlePanel.add(copyKeysButton);

        generateKeysButton.addActionListener(e -> generateKeys());
        copyKeysButton.addActionListener(e -> copyKeys());

        return middlePanel;
    }

    private JPanel bottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 1, 5, 5));
        bottomPanel.setBackground(Color.decode("#2B2B2B"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cipherTextLabel = new JLabel("Cipher Text:");
        cipherTextLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cipherTextLabel.setForeground(Color.WHITE);
        bottomPanel.add(cipherTextLabel);

        cipherTextField = new JTextField(10);
        cipherTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        cipherTextField.setEditable(false);
        bottomPanel.add(cipherTextField);

        cipherToHexLabel = new JLabel("Cipher Text to Hex:");
        cipherToHexLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cipherToHexLabel.setForeground(Color.WHITE);
        bottomPanel.add(cipherToHexLabel);

        cipherToHexField = new JTextField(10);
        cipherToHexField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        cipherToHexField.setEditable(false);
        bottomPanel.add(cipherToHexField);

        encryptButton = new JButton("Encrypt");
        encryptButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        encryptButton.setBackground(Color.decode("#3C3F41"));
        encryptButton.setForeground(Color.WHITE);
        bottomPanel.add(encryptButton);

        encryptButton.addActionListener(e -> encrypt());

        return bottomPanel;
    }

    private void generateKeys() {
        String first, second;
        crypto.generateRandomKeys();

        first = TextConversion.intToHexString(crypto.getfirstKey());
        second = TextConversion.intToHexString(crypto.getsecondKey());

        firstKeyField.setText(first);
        secondKeyField.setText(second);
    }

    /*
    * This method copies the generated keys to the system clipboard.    
    */
    private void copyKeys() {
        if(firstKeyField.getText().isEmpty() || secondKeyField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "No Keys to Copy. Please Generate Keys First.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String keys = firstKeyField.getText() + "," + secondKeyField.getText();
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(keys), null);

        JOptionPane.showMessageDialog(this, "Keys Copied", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /*
    * This method handles the encryption process when the encrypt button is clicked.
    */
    private void encrypt() {
        String plainText;
        StringBuilder cipherTextBuilder = new StringBuilder();
        String ciphrtToHex;

        if(firstKeyField.getText().isEmpty() || secondKeyField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both keys.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int firstKey = TextConversion.hexStringToInt(firstKeyField.getText());
        int secondKey = TextConversion.hexStringToInt(secondKeyField.getText());

        crypto.setFirstKey(firstKey);
        crypto.setSecondKey(secondKey);

        plainText = plainTextField.getText();
        if (plainText.isEmpty()) {
            cipherTextField.setText("");
            cipherToHexField.setText("");
            return;
        }

        int start = 0;
        int end = 2;
        // Process text in 2-character segment
        while (plainText.length() >= end) { 
            String segment = plainText.substring(start, end);
            int segmentInt = TextConversion.textToInt(segment);
            int encryptedSegment = crypto.encrypt(segmentInt);
            
            // Append to the StringBuilder
            cipherTextBuilder.append(TextConversion.intToText(encryptedSegment));

            start += 2;
            end += 2;
        }
        
        // Handle remaining characters if the text length isn't divisible by 2
        if (start < plainText.length()) {
            String segment = plainText.substring(start);
            // You might need padding logic here depending on your crypto implementation
            int segmentInt = TextConversion.textToInt(segment); 
            int encryptedSegment = crypto.encrypt(segmentInt);
            cipherTextBuilder.append(TextConversion.intToText(encryptedSegment));
        }

        ciphrtToHex = TextConversion.stringToHex(cipherTextBuilder.toString());
        
        // Convert builder back to String for display
        cipherTextField.setText(cipherTextBuilder.toString());
        cipherToHexField.setText(ciphrtToHex);
    }

    /*
    * This inner class listens for changes in the plainTextField and updates
    * the plainToHexField accordingly.
    */
    private class TextFieldListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateHexField();
            
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateHexField();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateHexField();
            
        }

        private void updateHexField(){
            String currentTxt, hexResult;
            currentTxt = plainTextField.getText();
            hexResult = TextConversion.stringToHex(currentTxt);
            plainToHexField.setText(hexResult);
        }
    }
}