package model;

import me.gosimple.nbvcxz.scoring.Result;

import java.security.GeneralSecurityException;
import java.util.List;

//Represents an entry of a user in the password management system which takes
// in name, username, password, url and notes as parameters

public class Entry {
    private static final String ALGORITHM = "SHA-256";
    private static Encryptor encryptor = Encryptor.getInstance();
    private static Decryptor decryptor = Decryptor.getInstance();
    private static Keyset keySet;
    private String name;
    private String username;
    private Password password;
    private String url;
    private String notes;
    private byte[] cryptoSaltBytes;

    //REQUIRES: name, username, url and notes of non-zero length
    // and password should not be null
    //EFFECTS: creates an entry object which instantiates all the fields with the
    // parameters passed into the constructor
    public Entry(String name, String username, Password password, String url, String notes) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.url = url;
        this.notes = notes;
        setUpEncryptionFields();
    }

    //EFFECTS: instantiates a new keySet with the given masterPassword and the ALGORITHM 'SHA-256'
    // otherwise it catches a GeneralSecurityException
    public static void instantiateKeySet(String masterPassword) {
        try {
            keySet = new Keyset(masterPassword, ALGORITHM);
        } catch (GeneralSecurityException e) {
            //throw new RuntimeException(e);
        }
    }

//    public Entry decrypt() throws GeneralSecurityException {
//        String name = decryptor.decrypt(this.name, cryptoSaltBytes, keySet);
//        String username = decryptor.decrypt(this.username, cryptoSaltBytes, keySet);
//        String password =
//                decryptor.decrypt(this.password.getPasswordString(), cryptoSaltBytes,
//                        keySet);
//        String url = decryptor.decrypt(this.url, cryptoSaltBytes, keySet);
//        String notes = decryptor.decrypt(this.notes, cryptoSaltBytes, keySet);
//        return new Entry(name, username, new Password(password), url, notes);
//    }

    //EFFECTS: encrypts a crypto salt byte
    private void setUpEncryptionFields() {
        cryptoSaltBytes = encryptor.createCryptoSalt();
    }

    //EFFECTS: returns the given password
    public Password getPassword() {
        return password;
    }

    //EFFECTS: returns the given name
    public String getName() {
        return name;
    }

    //EFFECTS: returns the given username
    public String getUsername() {
        return username;
    }

//    public String getPasswordString() {
//        return password.getPasswordString();
//    }

    //EFFECTS: returns the given URL
    public String getUrl() {
        return url;
    }

    //EFFECTS: returns the given notes
    public String getNotes() {
        return notes;
    }

    //EFFECTS: method to add different strings together to a StringBuilder and
    // convert to string
    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("Entry #" + (i + 1));
        sb.append("\nName: " + name);
        sb.append("\nUsername: " + username);
        sb.append("\nPassword: " + password.getPasswordString());
        sb.append("\nPassword rating: " + passRating());
        sb.append("\nURL: " + url);
        sb.append("\nNotes: " + notes);
        sb.append("\n");
        return sb.toString();
    }

    //EFFECTS: returns the entropy score of a password in the form of stars '*'
    private String passRating() {
        String stars = "";
        for (int i = 0; i < password.getScore() + 1; i++) {
            stars += "*";
        }
        return stars;
    }

    //EFFECTS: returns the system's suggestion on the strength of the password, its
    // entropy and number of guesses needed to crack the password
    public StringBuilder detailedView() {
        StringBuilder sb = new StringBuilder();
        List<String> suggestions = password.getFeedback().getSuggestion();
        sb.append("Suggestions: ");
        if (suggestions.size() == 0) {
            sb.append("None. Strong password!");
        } else {
            for (int i = 0; i < suggestions.size(); i++) {
                String s = suggestions.get(i);
                if (i == suggestions.size() - 1) {
                    sb.append(s);
                } else {
                    sb.append(s + ", ");
                }
            }
        }
        String warning = password.getFeedback().getWarning();
        sb.append("\nWarning: " + ((warning == null) ? "None. Strong password!" : warning));
        Result result = password.getResult();
        sb.append("\nPassword entropy (preferably higher): " + result.getEntropy().shortValue());
        sb.append("\nNumber of guesses to hack: " + result.getGuesses());
        return sb;
    }


}
