package security;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class DecryptionPanel extends JPanel{
    private CryptoLogic crypto;

    private JLabel cipherTextLabel;
    private JTextField cipherTextField;
    private JLabel cipherToHexLabel;
    private JTextField cipherToHexField;

    private JLabel keysLabel;
    private JTextField firstKeyField;
    private JTextField secondKeyField;
    private JButton pasteKeysButton;

    private JLabel plainTextLabel;
    private JTextField plainTextField;
    private JLabel plainToHexLabel;
    private JTextField plainToHexField;
    private JButton decryptButton;

    public DecryptionPanel() {
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

        cipherTextLabel = new JLabel("Cipher Text:");
        cipherTextLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cipherTextLabel.setForeground(Color.WHITE);
        topPanel.add(cipherTextLabel);
        
        cipherTextField = new JTextField(10);
        cipherTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        topPanel.add(cipherTextField);

        cipherToHexLabel = new JLabel("Cipher Text to Hex:");
        cipherToHexLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cipherToHexLabel.setForeground(Color.WHITE);
        topPanel.add(cipherToHexLabel);
        
        cipherToHexField = new JTextField(10);
        cipherToHexField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        cipherToHexField.setEditable(false);
        topPanel.add(cipherToHexField);

        cipherTextField.getDocument().addDocumentListener(new TextFieldListener());

        return topPanel;
    }

    private JPanel middlePanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(0, 1, 5, 5));
        middlePanel.setBackground(Color.decode("#2B2B2B"));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        keysLabel = new JLabel("Keys:");
        keysLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        keysLabel.setForeground(Color.WHITE);
        middlePanel.add(keysLabel);

        firstKeyField = new JTextField(10);
        firstKeyField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        middlePanel.add(firstKeyField);

        secondKeyField = new JTextField(10);
        secondKeyField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        middlePanel.add(secondKeyField);

        pasteKeysButton = new JButton("Paste Keys");
        pasteKeysButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        pasteKeysButton.setBackground(Color.decode("#3C3F41"));
        pasteKeysButton.setForeground(Color.WHITE);
        pasteKeysButton.addActionListener(e -> pasteKeys());
        middlePanel.add(pasteKeysButton);

        return middlePanel;
    }

    private JPanel bottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 1, 5, 5));
        bottomPanel.setBackground(Color.decode("#2B2B2B"));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        plainTextLabel = new JLabel("Plain Text:");
        plainTextLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        plainTextLabel.setForeground(Color.WHITE);
        bottomPanel.add(plainTextLabel);

        plainTextField = new JTextField(10);
        plainTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        plainTextField.setEditable(false);
        bottomPanel.add(plainTextField);

        plainToHexLabel = new JLabel("Plain Text to Hex:");
        plainToHexLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        plainToHexLabel.setForeground(Color.WHITE);
        bottomPanel.add(plainToHexLabel);

        plainToHexField = new JTextField(10);
        plainToHexField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        plainToHexField.setEditable(false);
        bottomPanel.add(plainToHexField);

        decryptButton = new JButton("Decrypt");
        decryptButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        decryptButton.setBackground(Color.decode("#3C3F41"));
        decryptButton.setForeground(Color.WHITE);
        decryptButton.addActionListener(e -> decryptText());
        bottomPanel.add(decryptButton);

        return bottomPanel;
    }

    private void pasteKeys() {
        try {
            Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String clipboardText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                
                String[] keys = clipboardText.split(",");
                if(keys.length == 2) {
                    firstKeyField.setText(keys[0].trim());
                    secondKeyField.setText(keys[1].trim());
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid keys format in clipboard.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to paste keys from clipboard.");
        }
    }

    private void decryptText() {
        String cipherText = cipherTextField.getText();
        String firstKey = firstKeyField.getText();
        String secondKey = secondKeyField.getText();

        if(firstKey.isEmpty() || secondKey.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both keys.");
            return;
        }

        // Parse hex strings to integers
        int firstKeyInt = Integer.parseInt(firstKey, 16);
        int secondKeyInt = Integer.parseInt(secondKey, 16);

        crypto.setFirstKey(firstKeyInt);
        crypto.setSecondKey(secondKeyInt);

        StringBuilder plainTextBuilder = new StringBuilder();
        StringBuilder plainToHexBuilder = new StringBuilder();

        int start = 0;
        int end = 2;

        // Process text in 2-character segments
        while(cipherText.length() >= end) {
            String segment = cipherText.substring(start, end);
            int segmentInt = TextConversion.textToInt(segment);
            int decryptedSegment = crypto.decrypt(segmentInt);

            plainTextBuilder.append(TextConversion.intToText(decryptedSegment));
            plainToHexBuilder.append(TextConversion.intToHexString(decryptedSegment));
            
            start += 2;
            end += 2;
        }

        // Handle remaining characters if the text length isn't divisible by 2
        if (start < cipherText.length()) {
            String segment = cipherText.substring(start);
            int segmentInt = TextConversion.textToInt(segment);
            int decryptedSegment = crypto.decrypt(segmentInt);
            plainTextBuilder.append(TextConversion.intToText(decryptedSegment));
            plainToHexBuilder.append(TextConversion.intToHexString(decryptedSegment));
        }

        // Convert builder back to String for display
        plainTextField.setText(plainTextBuilder.toString());
        plainToHexField.setText(plainToHexBuilder.toString());
    }

    /*
    * This inner class listens for changes in the cipherTextField and updates
    * the cipherToHexField accordingly.
    */
    private class TextFieldListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateTextField();
            
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateTextField();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateTextField();
            
        }

        private void updateTextField(){
            String text, hex;
            int binText;

            text = cipherTextField.getText();
            if (text.isEmpty()) { // Handle empty text safely
                cipherToHexField.setText("");
                return;
            }
            binText = TextConversion.textToInt(text);

            hex = TextConversion.intToHexString(binText);
            cipherToHexField.setText(hex);
        }
    }
}
