# Simple Feistel Cipher Demonstration

This is a Java Swing application that demonstrates a simplified implementation of a Feistel cipher. It provides a graphical user interface for visualizing encryption and decryption processes using a 4-round Feistel network.

## Features

- **Encryption & Decryption**: Encrypts and decrypts text based on the generated keys.
- **GUI Interface**: Easy-to-use Swing-based interface with separate panels for encryption and decryption.
- **Random Key Generation**: Automatically generates two 8-bit keys for the cipher rounds.
- **Technical Visualization**: Demonstrates the internal mechanics of a block cipher structure.

## Technical Details

The application implements a 16-bit block cipher using a Feistel structure:

- **Block Size**: 16 bits (splits input into 8-bit Left and Right halves).
- **Number of Rounds**: 4 rounds.
- **Key Schedule**: Uses two randomly generated 8-bit keys alternating each round.
- **Round Function F**: $F(R, K) = \text{RotateLeft}_8(R) \oplus K$
  - The function rotates the right half 1 bit to the left (circular shift on 8 bits) and XORs it with the round key.

## Prerequisites

- Java Development Kit (JDK) 21 or higher
- Maven (for building the project)

## Getting Started

### 1. Build the Project

Navigate to the project root directory and run:

```bash
mvn clean install
```

### 2. Run the Application

Execute the main class to launch the GUI:

```bash
mvn exec:java -Dexec.mainClass="security.Main"
```
Or run the generated JAR from the target directory if packaged.

## Project Structure

- **src/main/java/security/model**: Contains the core cryptographic logic (`CryptoLogic.java`).
- **src/main/java/security/view**: Contains the Swing UI components (`MainFrame`, `EncryptionPanel`, `DecryptionPanel`).
- **src/main/java/security/util**: Utility classes for text conversion.
- **src/main/java/security/Main.java**: Application entry point.