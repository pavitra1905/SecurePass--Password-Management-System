package model;

import java.security.SecureRandom;

//Represent an encryptor which is used to encrypt unencrypted items
public class Encryptor {
    private static Encryptor encryptor;

    private Encryptor() {
    }

    //EFFECTS: creates a new encryptor instance
    public static Encryptor getInstance() {
        encryptor = new Encryptor();
        return encryptor;
    }

    //EFFECTS: creates salt bytes which are an array of 16 random bytes
    public byte[] createCryptoSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] cryptoSaltBytes = new byte[16];
        sr.nextBytes(cryptoSaltBytes);
        return cryptoSaltBytes;
    }
}
