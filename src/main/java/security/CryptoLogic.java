package security;

import java.util.Random;

public class CryptoLogic {
    private int firstKey;
    private int secondKey;

    public CryptoLogic() {
        
    }

    public void generateRandomKeys() {
        Random random = new Random();
        
        // Key size = 16 bits (0 to 65535)
        this.firstKey = random.nextInt(65536); 
        this.secondKey = random.nextInt(65536); 
    }

    /*
     * Circular Left Shift for 8-bit integers.
     * We limit the input to 8 bits using & 0xFF.
     */
    private int rotateLeft8Bit(int value) {
        return ((value << 1) & 0xFF) | ((value >> 7) & 0x1);
    }

    public int encrypt(int plaintext) {
        int ciphertext;

        // Split 16-bit block into two 8-bit halves
        int left = (plaintext >> 8) & 0xFF;
        int right = plaintext & 0xFF;

        for(int i = 1; i <= 4; i++) {
            int roundKey = (i % 2 == 1) ? firstKey : secondKey;

            // Step A: Apply Function F
            // F(R, K) = Rotate(R) XOR CompressedKey
            
            // 1. Shift (Rotate Left by 1)
            int shiftedRight = rotateLeft8Bit(right);
            
            // 2. XOR with Key
            // Since the Right half is 8-bit but the Key is 16-bit, we "fold" the key 
            // by XORing its high 8 bits with its low 8 bits. 
            // This ensures the full 16-bit key is used without changing the block size.
            int key8Bit = ((roundKey >> 8) & 0xFF) ^ (roundKey & 0xFF);
            
            int f_result = shiftedRight ^ key8Bit;

            // Step B: XOR with Left to get New Right
            int newRight = left ^ f_result;

            // Step C: Shift/Swap
            int newLeft = right;

            // Update for next round
            left = newLeft;
            right = newRight;
        }

        // 4. Final Swap
        int temp = left;
        left = right;
        right = temp;

        // Combine Left and Right to form ciphertext
        ciphertext = (left << 8) | right;
        
        // Enforce 16-bit output
        return ciphertext & 0xFFFF;
    }

    public int decrypt(int ciphertext) {
        int plaintext;

        // Split 16-bit block into two 8-bit halves
        int left = (ciphertext >> 8) & 0xFF;
        int right = ciphertext & 0xFF;

        for(int i = 4; i >= 1; i--) {
            int roundKey = (i % 2 == 1) ? firstKey : secondKey;

            // 1. Shift (Rotate Left by 1)
            int shiftedRight = rotateLeft8Bit(right);
            
            // 2. XOR with Key (Folded to 8 bits)
            int key8Bit = ((roundKey >> 8) & 0xFF) ^ (roundKey & 0xFF);
            
            int f_result = shiftedRight ^ key8Bit;

            // Step B: XOR with Left to get New Right
            int newRight = left ^ f_result;

            // Step C: Shift/Swap
            int newLeft = right;

            // Update for next round
            left = newLeft;
            right = newRight;
        }

        // 4. Final Swap
        int temp = left;
        left = right;
        right = temp;

        // Combine Left and Right to form plaintext
        plaintext = (left << 8) | right;
        
        // Enforce 16-bit output
        return plaintext & 0xFFFF;
    }

    public int getfirstKey() {
        return firstKey;
    }

    public int getsecondKey() {
        return secondKey;
    }

    public void setFirstKey(int firstKey) {
        this.firstKey = firstKey;
    }

    public void setSecondKey(int secondKey) {
        this.secondKey = secondKey;
    }
}