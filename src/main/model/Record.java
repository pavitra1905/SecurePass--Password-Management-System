package model;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

//Represents a record which consists of all the entries made into the Password Management System
public class Record {
    private ArrayList<Entry> entries;
    private EventLog dataRecord = EventLog.getInstance();

    //EFFECTS: constructs an empty Record object with an empty list of entries
    public Record() {
        entries = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: initializes the record object, entries with the provided entries list
    public Record(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    //EFFECT: returns the list of entries
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    //MODIFIES: this
    //EFFECTS: replaces the list of entries in memory with the provided list of entries
    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    //MODIFIES: this
    //EFFECTS: adds an entry to the list of entries and also to the dataRecord
    public void addEntry(Entry entry) {
        entries.add(entry);
        //dataRecord.addEntry(entries.size(), entry.getName());
        EventLog.getInstance().logEvent(new Event("Added entry #" + entries.size()
                + " with name " + entry.getName() + "."));
    }

    //MODIFIES: this
    //EFFECTS: removes an entry from the list of entries and also from dataRecord
    public void removeEntry(int i) {
        String nameOfEntry = entries.get(i).getName();
        entries.remove(i);
        //dataRecord.removeEntry(++i, nameOfEntry);
        EventLog.getInstance().logEvent(new Event("Removed entry #" + ++i
                + " with name " + nameOfEntry + "."));
    }

    //EFFECTS: returns the size of the entries list
    public int getLengthOfEntries() {
        return entries.size();
    }

    //EFFECTS: returns an entry at a given index provided
    public Entry getEntryAtIndex(int i) {
        return entries.get(i);
    }

    //EFFECTS: returns the complete entry with the detailed view at an index in the form of a string
    public String viewEntry(int index) throws IndexOutOfBoundsException {
        Entry e = entries.get(index);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(e.toString(index));
        stringBuilder.append(e.detailedView().toString());
        return stringBuilder.toString();
    }

//    public String toJson() {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.writerWithDefaultPrettyPrinter()
//                    .writeValueAsString(entries);
//        } catch (JsonProcessingException e) {
//            System.out.println(
//                    "Error in saving entries. Entries were not saved.");
//        }
//        return null;
//    }

    //EFFECTS: converts the record and its entries into a JSON format
    public JSONObject toJson() {
        JSONObject recordJson = new JSONObject();
        JSONArray entriesJsonArray = new JSONArray();
        for (Entry e : entries) {
            JSONObject entryJson = new JSONObject();
            entryJson.put("name", e.getName());
            entryJson.put("username", e.getUsername());
            entryJson.put("password", e.getPassword().getPasswordString());
            entryJson.put("url", e.getUrl());
            entryJson.put("notes", e.getNotes());
            entryJson.put("entropy", e.detailedView());
            entriesJsonArray.put(entryJson);
        }
        recordJson.put("entries", entriesJsonArray);
        return recordJson;
    }

    //EFFECTS: prints the log of events
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}
