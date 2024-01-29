package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    void runBefore() {
        e1 = new Event("D1");
        e2 = new Event("D2");
        e3 = new Event("D3");
        EventLog dr = EventLog.getInstance();
        dr.recordData(e1);
        dr.recordData(e2);
        dr.recordData(e3);
    }

    @Test
    public void testRecordData() {
        List<Event> l = new ArrayList<Event>();
        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }
        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
        assertTrue(l.contains(e3));
    }

    @Test
    public void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> i = el.iterator();
        assertTrue(i.hasNext());
        assertEquals("Event log cleared.", i.next().getDescription());
        assertFalse(i.hasNext());
    }

}
