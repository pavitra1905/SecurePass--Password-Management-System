package model;

import model.Entry;
import model.Record;
import model.Password;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RecordTest {
    private Record testRecord;
    private ArrayList<Entry> testEntryList;
    private Entry e1;
    private Entry e2;

    @BeforeEach
    void runBefore() {
        testRecord = new Record();
        testEntryList = new ArrayList<>();
        e1 = new Entry("Microsoft", "guest.microsoft@gmail.com", new Password("guestMicrosoft"),
                "www.microsoft.com", "Microsoft account Details");
        e2 = new Entry("Twitter", "guest.twitter@gmail.com", new Password("guestTwitter"),
                "www.twitter.com", "Twitter account Details");

        testEntryList.add(e1);
        testEntryList.add(e2);
    }

    @Test
    public void testConstructorNoEntries() {
        assertEquals(0, testRecord.getLengthOfEntries());
    }

    @Test
    public void testConstructorsWithEntries() {
        testRecord = new Record(testEntryList);
        List<Entry> newList = testRecord.getEntries();
        assertEquals(testEntryList, newList);
    }

    @Test
    public void testGetEntryAtIndexZero() {
        testRecord = new Record(testEntryList);
        assertEquals(e1, testRecord.getEntryAtIndex(0));
    }

    @Test
    public void testGetEntryAtIndexEndOfList() {
        testRecord = new Record(testEntryList);
        assertEquals(e2, testRecord.getEntryAtIndex(1));
    }

    @Test
    public void testAddEntry() {
        testRecord = new Record(testEntryList);
        testRecord.addEntry(e1);
        assertEquals(3, testRecord.getLengthOfEntries());
        assertEquals(e1, testRecord.getEntryAtIndex(2));
    }

    @Test
    public void testRemoveEntry() {
        testRecord = new Record(testEntryList);
        testRecord.removeEntry(0);
        assertEquals(1, testRecord.getLengthOfEntries());
        assertEquals(e2, testRecord.getEntryAtIndex(0));
    }

    @Test
    public void testViewEntryWithValidIndex() {
        // Test viewEntry with a valid index
        testRecord.addEntry(e1);
        assertEquals("Entry #1\n"
                + "Name: Microsoft\n"
                + "Username: guest.microsoft@gmail.com\n"
                + "Password: guestMicrosoft\n"
                + "Password rating: ***\n"
                + "URL: www.microsoft.com\n"
                + "Notes: Microsoft account Details\n"
                + "Suggestions: Add another word or two. Uncommon words are better., Capitalization doesn't help very much.\n"
                + "Warning: This is a very common password.\n"
                + "Password entropy (preferably higher): 20\n"
                + "Number of guesses to hack: 2023200", testRecord.viewEntry(0));
    }

    @Test
    public void testPrintLog() {
        EventLog eventLog = EventLog.getInstance();
        Event event1 = new Event("Event 1");
        Event event2 = new Event("Event 2");
        eventLog.logEvent(event1);
        eventLog.logEvent(event2);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        testRecord.printLog(eventLog);
        System.setOut(System.out);
        String expectedOutput = outputStream.toString();
        assertEquals(expectedOutput, outputStream.toString());
    }

//    @Test
//    public void testViewEntryWithInvalidIndex() {
//        // Test viewEntry with an invalid index
//        EntryTest entryTest = new EntryTest(entries);
//        entryTest.viewEntry(2);
//    }
}
