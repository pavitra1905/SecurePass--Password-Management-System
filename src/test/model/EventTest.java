package model;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    void runBefore() {
        e = new Event("Microsoft account details saved");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Microsoft account details saved", e.getDescription());
        long dateLong = d.getTime();
        long entry = e.getDate().getTime();
        long difference = 25;


        if (!(dateLong - difference < entry && dateLong + difference > entry)) {
            fail("");
        }
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Microsoft account details saved", e.toString());
    }

}
