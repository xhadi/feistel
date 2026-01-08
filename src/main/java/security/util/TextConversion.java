package security.util;

public class TextConversion {
    // Converts a 2-character string to a 16-bit integer
    public static int textToInt(String text) {
        int result = 0;
        for(int i = 0; i < text.length(); i++) {
            result = (result << 8) | (text.charAt(i) & 0xFF);
        }
        return result;
    }

    // Converts a 16-bit integer to a 2-character string
    public static String intToText(int value) {
        char[] chars = new char[2];
        chars[0] = (char) ((value >> 8) & 0xFF);
        chars[1] = (char) (value & 0xFF);
        return new String(chars);
    }

    // Converts a 16-bit integer to a 2-character hexadecimal string
    public static String intToHexString(int value) {
        return String.format("%02X", value);
    }

    // Converts a 2-character hexadecimal string to a 16-bit integer
    public static int hexStringToInt(String hex) {
        return Integer.parseInt(hex, 16);
    }

    // Converts a full String to a Hex String (for the UI display)
    public static String stringToHex(String input) {
        if (input == null) return "";
        StringBuilder hex = new StringBuilder();
        for (char c : input.toCharArray()) {
            // %02X formats the character as 2 Hex digits (e.g., 'A' -> "41")
            hex.append(String.format("%02X", (int) c));
        }
        return hex.toString();
    }
}