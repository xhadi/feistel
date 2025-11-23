package security;

import java.util.Random;

public class CryptoLogic {
    private int firstKey;
    private int secondKey;

    public CryptoLogic() {
        
    }

    public void generateRandomKeys() {
        Random random = new Random();
        
        this.firstKey = random.nextInt(256); 
        this.secondKey = random.nextInt(256); 
    }

    public int encrypt(int plaintext) {
        int ciphertext;

        int left = (plaintext >> 8) & 0xFF;
        int right = plaintext & 0xFF;

        for(int i = 1; i <= 4; i++) {
            int roundKey = (i % 2 == 1) ? firstKey : secondKey;

            // Step A: Apply Function F (XOR)
            // F(R, K) = R XOR K
            int f_result = right ^ roundKey;

            // Step B: XOR with Left to get New Right
            // NewRight = L XOR F(R, K)
            int newRight = left ^ f_result;

            // Step C: Shift/Swap
            // The old Right becomes the new Left
            int newLeft = right;

            // Update for next round
            left = newLeft;
            right = newRight;
        }

        // 4. Final Swap (Standard Feistel requirement to make decryption symmetric)
        // We swap Left and Right one last time to form the ciphertext
        int temp = left;
        left = right;
        right = temp;

        // Combine Left and Right to form ciphertext
        ciphertext = (left << 8) | right;
        
        return ciphertext;
    }

    public int decrypt(int ciphertext) {
        int plaintext;

        int left = (ciphertext >> 8) & 0xFF;
        int right = ciphertext & 0xFF;

        for(int i = 4; i >= 1; i--) {
            int roundKey = (i % 2 == 1) ? firstKey : secondKey;

            // Step A: Apply Function F (XOR)
            // F(R, K) = R XOR K
            int f_result = right ^ roundKey;

            // Step B: XOR with Left to get New Right
            // NewRight = L XOR F(R, K)
            int newRight = left ^ f_result;

            // Step C: Shift/Swap
            // The old Right becomes the new Left
            int newLeft = right;

            // Update for next round
            left = newLeft;
            right = newRight;
        }

        // 4. Final Swap (Standard Feistel requirement to make decryption symmetric)
        // We swap Left and Right one last time to form the plaintext
        int temp = left;
        left = right;
        right = temp;

        // Combine Left and Right to form plaintext
        plaintext = (left << 8) | right;
        
        return plaintext;
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