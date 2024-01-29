package model;

import model.Entry;
import model.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.GenericSignatureFormatError;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntryTest {
    private Entry testEntry;
    private Password testPassword;

    @BeforeEach
    void runBefore() {
        testPassword = new Password("guest");
        testEntry = new Entry("YouTube", "guest@gmail.com", testPassword,
                "www.youtube.com", "YouTube Channel Details");
    }

    @Test
    public void testConstructor() {
        assertEquals("YouTube", testEntry.getName());
        assertEquals("guest@gmail.com", testEntry.getUsername());
        assertEquals(testPassword, testEntry.getPassword());
        assertEquals("www.youtube.com", testEntry.getUrl());
        assertEquals("YouTube Channel Details", testEntry.getNotes());
    }

    @Test
    public void testStrongPassword() {
        Password p = new Password("12hfi382ASDM22gfu8$&*$92bfomfhf*");
        Entry newEntry = new Entry ("Google", "guest@gmail.com", p, "www.gmail.com",
                "Google Mail Details");
        StringBuilder suggestions = newEntry.detailedView();
        String sString = suggestions.toString();
        String expected = "Suggestions: None. Strong password!\n"
                + "Warning: None. Strong password!\n"
                + "Password entropy (preferably higher): 138\n"
                + "Number of guesses to hack: 443727775817350300000000000000000000000000";
        assertEquals(sString, expected);
    }

    @Test
    public void testInstantiateKeySet() {
        try {
            Keyset keyset = new Keyset(null, "AHA-449");
        } catch (GeneralSecurityException e) {
            assertThrows(RuntimeException.class, () -> {Entry.instantiateKeySet(null);});
            System.out.println("caught");
        }
    }

}