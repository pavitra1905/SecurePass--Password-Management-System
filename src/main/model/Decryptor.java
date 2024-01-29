package model;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

//Represents a decryptor which is used to decrypt encrypted items
public class Decryptor {
    private static Decryptor decryptor;

    private Decryptor() {
    }

    //EFFECTS: creates a new decryptor instance (will be used later for JsonReader)
    public static Decryptor getInstance() {
        decryptor = new Decryptor();
        return decryptor;
    }

//    //REQUIRES: parameters field, salt and keyset should not be null
//    //EFFECTS: decrypts the filed using keyset and returns the decrypted string
//    public String decrypt(String field, byte[] salt, Keyset keyset)
//            throws GeneralSecurityException,
//            GeneralSecurityException {
//        byte[] encodedBytes = ByteConvertor.stringToByte(field);
//        byte[] decryptedBytes = keyset.decrypt(encodedBytes, salt);
//        return new String(decryptedBytes, StandardCharsets.UTF_8);
//    }
}
