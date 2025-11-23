package security;

public class TextConversion {

    public static int textToInt(String text) {
        int result = 0;
        for(int i = 0; i < text.length(); i++) {
            result = (result << 8) | (text.charAt(i) & 0xFF);
        }
        return result;
    }

    public static String intToText(int value) {
        char[] chars = new char[2];
        chars[0] = (char) ((value >> 8) & 0xFF);
        chars[1] = (char) (value & 0xFF);
        return new String(chars);
    }

    public static String intToHexString(int value) {
        return String.format("%04X", value);
    }
}