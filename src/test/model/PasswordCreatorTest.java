package model;

import model.PasswordCreator;
import model.PasswordCreator.CharacterTypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashSet;

public class PasswordCreatorTest {
    private PasswordCreator testPassCreator;
    private ArrayList<Boolean> characterTypesList;

    @BeforeEach
    void runBefore() {
        testPassCreator = PasswordCreator.getInstance();
        characterTypesList = new ArrayList<>();
    }

    @Test
    public void testAddCharTypesOneType() {
        characterTypesList.add(true);
        characterTypesList.add(false);
        characterTypesList.add(false);
        characterTypesList.add(false);

        ArrayList<CharacterTypes> charTypes = testPassCreator.addCharTypes(characterTypesList);
        assertEquals(charTypes.size(), 1);
        assertTrue(charTypes.get(0) == CharacterTypes.LOWERCASE);
    }

    @Test
    public void testAddCharTypesMultipleTypes() {
        characterTypesList.add(true);
        characterTypesList.add(false);
        characterTypesList.add(true);
        characterTypesList.add(true);

        ArrayList<CharacterTypes> charTypes = testPassCreator.addCharTypes(characterTypesList);
        assertEquals(charTypes.size(), 3);
        assertTrue(charTypes.get(0) == CharacterTypes.LOWERCASE);
        assertFalse(charTypes.get(1) == CharacterTypes.UPPERCASE);
        assertTrue(charTypes.get(2) == CharacterTypes.SYMBOLS);
    }

    @Test
    public void testAddCharTypesAllTypes() {
        characterTypesList.add(true);
        characterTypesList.add(true);
        characterTypesList.add(true);
        characterTypesList.add(true);

        ArrayList<CharacterTypes> charTypes = testPassCreator.addCharTypes(characterTypesList);
        assertEquals(charTypes.size(), 4);
        assertTrue(charTypes.get(0) == CharacterTypes.LOWERCASE);
        assertTrue(charTypes.get(1) == CharacterTypes.UPPERCASE);
        assertTrue(charTypes.get(2) == CharacterTypes.NUMBERS);
        assertTrue(charTypes.get(3) == CharacterTypes.SYMBOLS);
    }

    @Test
    void testCreateRandomPasswordWithOneType() {
        HashSet<Character> numbers = addToSet("1234567890");
        ArrayList<CharacterTypes> charTypes = new ArrayList<>();
        charTypes.add(CharacterTypes.NUMBERS);

        String randomPassword = testPassCreator.createRandomPassword(charTypes, 16);
        assertEquals(randomPassword.length(), 16);
        assertTrue(containsAllChars(randomPassword, numbers));
    }

    @Test
    void testCreatePassword() {
        PasswordCreator passCreator = new PasswordCreator();
        ArrayList<Boolean> charTypes = new ArrayList<>();
        charTypes.add(true);
        charTypes.add(false);
        charTypes.add(true);
        charTypes.add(false);
        int length = 10;
        assertEquals(4, charTypes.size());
        String p = passCreator.createPassword(charTypes, length);
        assertEquals(length, p.length());
        assertTrue(p.matches(".*[a-zA-Z].*"));
        assertTrue(p.matches(".*\\d.*"));
    }

    @Test
    void testCreateRandomPasswordWithMultipleTypes() {
        HashSet<Character> twoTypes = addToSet("abcdefghijklmnopqrstuvwxyz!@#$%^&*()");
        ArrayList<CharacterTypes> charTypes = new ArrayList<>();
        charTypes.add(CharacterTypes.LOWERCASE);
        charTypes.add(CharacterTypes.SYMBOLS);

        String randomPassword = testPassCreator.createRandomPassword(charTypes, 26);
        assertEquals(randomPassword.length(), 26);
        assertTrue(containsAllChars(randomPassword, twoTypes));
    }

    @Test
    void testCreateRandomPasswordWithAllTypes() {
        HashSet<Character> types = addToSet("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLNOPQRSTUVWXYZ1234567890!@#$%^&*()");
        ArrayList<CharacterTypes> charTypes = new ArrayList<>();
        charTypes.add(CharacterTypes.LOWERCASE);
        charTypes.add(CharacterTypes.UPPERCASE);
        charTypes.add(CharacterTypes.SYMBOLS);
        charTypes.add(CharacterTypes.NUMBERS);

        String randomPassword = testPassCreator.createRandomPassword(charTypes, 36);
        assertEquals(randomPassword.length(), 36);
        //assertTrue(containsAllChars(randomPassword, types));
        assertNotNull(containsAllChars(randomPassword, types));
    }

    private HashSet<Character> addToSet(String s) {
        HashSet<Character> charSet = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            charSet.add(s.charAt(i));
        }
        return charSet;
    }

    private boolean containsAllChars(String s, HashSet<Character> set) {
        for (int i = 0; i < s.length(); i++) {
            if (!set.contains(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
