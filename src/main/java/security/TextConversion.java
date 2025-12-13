package security;

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

    // Converts a 16-bit integer to a 4-digit hexadecimal string
    public static String intToHexString(int value) {
        return String.format("%04X", value);
    }

    // Converts a 4-digit hexadecimal string to a 16-bit integer
    public static int hexStringToInt(String hex) {
        return Integer.parseInt(hex, 16);
    }
}