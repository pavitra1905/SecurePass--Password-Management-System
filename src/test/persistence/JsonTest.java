package persistence;

import model.Entry;
import model.Password;


import static org.junit.jupiter.api.Assertions.*;

//EFFECTS: checks if each field in entry has the correct item stored in it
public class JsonTest {
    protected void checkEntry(String name, String username, Password password, String url,
                              String notes, Entry entry) {
        assertEquals(name, entry.getName());
        assertEquals(username, entry.getUsername());
        assertEquals(password.getPasswordString(), entry.getPassword().getPasswordString());
        assertEquals(url, entry.getUrl());
        assertEquals(notes, entry.getNotes());
    }
}
