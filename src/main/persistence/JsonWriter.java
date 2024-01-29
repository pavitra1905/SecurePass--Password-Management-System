package persistence;

import model.Entry;
import model.Record;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Represents a writer that writes Json representation of a record object to the file
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs a writer to write to the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws a FileNotFoundException if destination file cannot be
    // opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of record to file
    public void write(Record r, String masterPassword) {
        Entry.instantiateKeySet(masterPassword);
        //String json = r.toJson();
        JSONObject json = r.toJson();
        saveToFile(json);
//        EventLog.getInstance().recordData(new Event("Saved entries to workroom.json."));
        EventLog.getInstance().logEvent(new Event("Saved entries to workroom.json."));
    }

//    private void saveToFile(String json) {
//        writer.print(json);
//    }

    //MODIFIES: this
    //EFFECTS: writes JSONObject to file
    private void saveToFile(JSONObject json) {
        writer.print(json);
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}
