package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

//Represents a record of the data entered in the system.
//It follows the Singleton Design Pattern which ensures that one DataRecord has
//global access to a single instance of DataRecord.

public class EventLog implements Iterable<Event> {
    /**
     * the only EventLog in the system (Singleton Design Pattern)
     */
    private static EventLog theLog;
    private Collection<Event> events;

    //constructor for DataRecord
    public EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     *
     * @return instance of EventLog
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    /**
     * Adds an event to the event log.
     *
     * @param e the event to be added
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    //MODIFIES: this
    //EFFECTS: clears the data record and records it
    public void clear() {
        events.clear();
        //recordData(new Event("Event log cleared."));
        logEvent(new Event("Event log cleared."));
    }

    //MODIFIES: this
    //EFFECTS: adds a data entry to the record
    public void recordData(Event d) {
        events.add(d);
    }

    //EFFECTS: iterator method to iterate through the records
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }

//    //EFFECTS: adds a new entry to the data record
//    public void addEntry(int size, String name) {
//        EventLog.getInstance().recordData(
//                new Event("Added entry #" + size + " with name " + name + "."));
//    }
//
//    //EFFECTS: removes an entry from the data record
//    public void removeEntry(int index, String name) {
//        EventLog.getInstance().recordData(new Event("Removed entry #" + ++index
//                + " with name " + name + "."));
//    }

}
