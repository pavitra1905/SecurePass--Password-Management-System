package model;

import me.gosimple.nbvcxz.resources.Generator;

import java.security.SecureRandom;
import java.util.ArrayList;

//Represents a random password creator. It extends the Generator class in the Nbvcxz library
// to create a random password.
public class PasswordCreator extends Generator {
    private static PasswordCreator passCreator;

    public PasswordCreator() {

    }

    //EFFECTS: create an instance of PasswordCreator if it does not already exist.
    public static PasswordCreator getInstance() {
        if (passCreator == null) {
            passCreator = new PasswordCreator();
        }
        return passCreator;
    }

    //EFFECTS: returns a random password with given the character types and length
    public String createPassword(ArrayList<Boolean> charTypes, int length) {
        ArrayList<CharacterTypes> ct = addCharTypes(charTypes);
        return createRandomPassword(ct, length);
    }

    // REQUIRES: charTypes has size 4
    //EFFECTS: method to add character types to a list and return it
    public ArrayList<CharacterTypes> addCharTypes(ArrayList<Boolean> charTypes) {
        ArrayList<CharacterTypes> ct = new ArrayList<>();

        addCharIfTrue(charTypes, 0, CharacterTypes.LOWERCASE, ct);
        addCharIfTrue(charTypes, 1, CharacterTypes.UPPERCASE, ct);
        addCharIfTrue(charTypes, 2, CharacterTypes.NUMBERS, ct);
        addCharIfTrue(charTypes, 3, CharacterTypes.SYMBOLS, ct);
        return ct;
    }

    //REQUIRES: characterTypes is one of the valid values (lowercase, uppercase, symbols, numbers);
    // length of list > 0
    //EFFECTS: creates a random password of the given length including the chosen character types
    public String createRandomPassword(ArrayList<CharacterTypes> characterTypes, int length) {
        StringBuilder sb = new StringBuilder();
        String characters = createCharString(characterTypes);

        int charLength = characters.length();
        SecureRandom sr = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = sr.nextInt(charLength);
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    //REQUIRES: charTypesList has 4 elements
    //EFFECTS: adds the character type to ct if the boolean value is true in charTypesList
    private void addCharIfTrue(ArrayList<Boolean> charTypesList, int index, CharacterTypes charType,
                               ArrayList<CharacterTypes> ct) {
        if (charTypesList.get(index)) {
            ct.add(charType);
        }
    }

    //REQUIRES: characterTypes has at least 1 element
    //EFFECTS: creates a string with all the character types given by thhe user
    private String createCharString(ArrayList<CharacterTypes> characterTypes) {
        StringBuilder sb = new StringBuilder();
        for (CharacterTypes ct: characterTypes) {
            switch (ct) {
                case LOWERCASE:
                    sb.append("abcdefghijklmnopqrstuvwxyz");
                    break;
                case UPPERCASE:
                    sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                    break;
                case SYMBOLS:
                    sb.append("!@#$%^&*()");
                    break;
                default:
                    sb.append("1234567890");
                    break;
            }
        }
        return sb.toString();
    }

    //EFFECTS: enumeration of the 4 possible character types given as an option to include in
    // a password
    public enum CharacterTypes {
        LOWERCASE,
        UPPERCASE,
        SYMBOLS,
        NUMBERS;

        private CharacterTypes() {

        }
    }
}
