package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.GeneralSecurityException;

public class KeysetTest {
    private Keyset keyset;

    @BeforeEach
    public void setUp() throws GeneralSecurityException {
        // Initialize the Keyset with a sample password and algorithm
        keyset = new Keyset("SamplePassword", "SHA-256");
    }

    @Test
    public void testEncryptAndDecrypt() throws GeneralSecurityException {
        // Test encryption and decryption with a known value
        byte[] cryptoSaltBytes = new byte[16]; // Sample salt
        String passwordString = "SamplePasswordString";

        byte[] encryptedBytes = keyset.encrypt(passwordString, cryptoSaltBytes);
        assertNotNull(encryptedBytes);

        byte[] decryptedBytes = keyset.decrypt(encryptedBytes, cryptoSaltBytes);
        assertNotNull(decryptedBytes);

        String decryptedString = new String(decryptedBytes);
        assertEquals(passwordString, decryptedString);
    }

    @Test
    public void testEncryptWithInvalidInput() {
        // Test encrypt with an invalid input
        byte[] cryptoSaltBytes = new byte[16]; // Sample salt
        String invalidPasswordString = null;

        byte[] encryptedBytes = keyset.encrypt(invalidPasswordString, cryptoSaltBytes);
        assertNull(encryptedBytes);
    }
}