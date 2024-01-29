package persistence;

import com.google.api.client.json.Json;
import model.Password;
import model.Record;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import model.Entry;
import model.Record;

public class JsonWriterTest extends JsonTest{

    @Test
    public void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
        try {
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            //assertTrue(e.getMessage().contains("invalid.json"));
        }
    }

    @Test
    public void testWriterEmptyFile() {
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyFile.json");
        try {
            String masterPassword = "password";
            ArrayList<Entry> entries = new ArrayList<>();
            Record r = new Record(entries);
            writer.open();
            writer.write(r, masterPassword);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyFile.json");
            r = reader.read("password", "STORE");
            assertEquals(0, r.getLengthOfEntries());
        } catch (IOException e) {
            fail("IOException caught unexpectedly");
        }
    }

    @Test
    public void testWriterGeneralRecord() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecord.json");
            String masterPassword = "password";
            ArrayList<Entry> entries = new ArrayList<>();
            Record r = new Record(entries);
            Entry e1 = new Entry("YouTube", "guest1@gmail.com", new Password("guest123"),
                    "www.youtube.com", "YouTube account details");
            Entry e2 = new Entry("Google", "guest2@gmail.com", new Password("guest123"),
                    "www.google.com", "Google account details");
            r.addEntry(e1);
            r.addEntry(e2);
            writer.open();
            writer.write(r, masterPassword);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralRecord.json");
            r = reader.read("password", "STORE");
            entries = r.getEntries();
            assertEquals(2, entries.size());
            checkEntry("YouTube", "guest1@gmail.com", new Password("guest123"),
                    "www.youtube.com", "YouTube account details", entries.get(0));
            checkEntry("Google", "guest2@gmail.com", new Password("guest123"),
                    "www.google.com", "Google account details", entries.get(1));
        } catch (FileNotFoundException e) {
            fail("FileNoFoundException caught unexpectedly");
        } catch (IOException e) {
            fail("IOException caught unexpectedly");
        }
    }
}
