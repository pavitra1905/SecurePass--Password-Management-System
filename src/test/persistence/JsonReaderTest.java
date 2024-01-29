package persistence;

import model.Entry;
import model.Record;
import model.Password;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Record record = reader.read("password", "STORE");
            fail("IOException expected");
        } catch (IOException e) {
        }
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyFile.json");
        try {
            Record record = reader.read("passwordEmptyFile", "STORE");
            assertEquals(0, record.getLengthOfEntries());
        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }

    @Test
    void testReaderGeneralRecord() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralRecord.json");
        try {
            Record record = reader.read("passwordGeneralFile", "STORE");
            List<Entry> entries = record.getEntries();
            assertEquals(2, entries.size());
            checkEntry("YouTube", "guest1@gmail.com", new Password("guest123"),
                    "www.youtube.com", "YouTube account details", entries.get(0));
            checkEntry("Google", "guest2@gmail.com", new Password("guest123"),
                    "www.google.com", "Google account details", entries.get(1));
        } catch (IOException e) {
            fail("Could not read from file.");
        }
    }
}
