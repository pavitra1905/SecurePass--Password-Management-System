package model;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.subtle.AesGcmJce;

//Represents a Keyset object to implement with the Tink library to use the master
// password feature

public class Keyset {
    private AesGcmJce aead;
    private ByteConvertor byteCon;

    //REQUIRES: password and algorithm parameters should not be null, and
    // algorithm is a valid algorithm that Message Digest recognises
    public Keyset(String password, String algorithm)
            throws GeneralSecurityException {
        byteCon = new ByteConvertor();
        AeadConfig.register();
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] key = convertToBits(messageDigest.digest());
        aead = new AesGcmJce(key);
    }

    //REQUIRES: bytes has at least 16 elements
    //EFFECTS: concatenates bytes into 128 bits
    private byte[] convertToBits(byte[] bytes) {
        byte[] key = new byte[16];
        System.arraycopy(bytes, 0, key, 0, 16);
        return key;
    }

    //REQUIRES: passwordString and cryptoSatBytes should not be null
    //EFFECTS: encrypts the password string into an encrypted array of bytes
    public byte[] encrypt(String passwordString, byte[] cryptoSaltBytes) {
        try {
            return aead.encrypt(passwordString.getBytes(StandardCharsets.UTF_8),
                    cryptoSaltBytes);
        } catch (Exception e) {
            return null;
        }
    }

    //REQUIRES: encodedBytes and cryptoSaltBytes are not null
    //EFFECTS: decrypts the encrypted byte array into a string
    public byte[] decrypt(byte[] encodedBytes, byte[] cryptoSaltBytes)
            throws GeneralSecurityException {
        return aead.decrypt(encodedBytes, cryptoSaltBytes);
    }

}
