package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Represents a reader that reads a record object from the Json data stored
public class JsonReader {
    private String source;

    //EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

//    public Record read(String masterPassword, String store)
//            throws IOException, GeneralSecurityException {
//        String jsonData = readFile(source);
//        ObjectMapper mapper = new ObjectMapper();
//        List<Entry> encryptedEntriesLoaded = mapper.readValue(jsonData, new TypeReference<List<Entry>>() {
//        });
//        List<Entry> entriesLoaded = new ArrayList<>();
//        try {
//            decryptEntries(encryptedEntriesLoaded, entriesLoaded, masterPassword);
//            DataRecord.getInstance().recordData(new Data("Loaded entries from workroom.json"));
//            System.out.println("Loaded file from " + store);
//        } catch (GeneralSecurityException e) {
//            DataRecord.getInstance().recordData(new Data("Failed to authenticate password to lead entries"));
//            throw new GeneralSecurityException("Bad password!");
//        }
//        return parseFile(entriesLoaded);
//    }

    //EFFECTS: reads a record from the file and returns it;
    // throws IOException and GeneralSecurityException if an error occurred while reading data from file and password
    // could not be authenticated respectively
//    public Record read(String masterPassword, String store)
//            throws IOException, GeneralSecurityException {
//        JSONObject jsonData = readJsonObject(source);
//        JSONArray encryptedEntriesLoaded = jsonData.getJSONArray("entries");
//        List<Entry> entriesLoaded = new ArrayList<>();
//        try {
//            decryptEntries(encryptedEntriesLoaded, entriesLoaded, masterPassword);
//            DataRecord.getInstance().recordData(new Data("Loaded entries from workroom.json"));
//            System.out.println("Loaded file from " + store);
//        } catch (GeneralSecurityException e) {
//            DataRecord.getInstance().recordData(new Data("Failed to authenticate password to lead entries"));
//            throw new GeneralSecurityException("Bad password!");
//        }
//        return parseFile(entriesLoaded);
//    }

    //EFFECTS: reads a record from the file and returns it;
    public Record read(String masterPassword, String store)
            throws IOException {
        JSONObject jsonData = readJsonObject(source);
        JSONArray encryptedEntriesLoaded = jsonData.getJSONArray("entries");
        ArrayList<Entry> entriesLoaded = new ArrayList<>();
        decryptEntries(encryptedEntriesLoaded, entriesLoaded, masterPassword);
//        EventLog.getInstance().recordData(new Event("Loaded entries from workroom.json"));
        EventLog.getInstance().logEvent(new Event("Loaded entries from workroom.json."));
        //System.out.println("Loaded file from " + store);
        return parseFile(entriesLoaded);
    }

    //EFFECTS: reads the source file as a JSONObject and returns it else
    // it throws an IOException
    private JSONObject readJsonObject(String source) throws IOException {
        String jsonContent = readFile(source);
        return new JSONObject(jsonContent);
    }

//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//        try (Stream<String> stream = Files.lines(Paths.get(source),
//                StandardCharsets.UTF_8)) {
//            stream.forEach(s -> contentBuilder.append(s));
//        }
//        return contentBuilder.toString();
//    }

    //EFFECTS: reads a source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        }
        return contentBuilder.toString();
    }

//    private void decryptEntries(List<Entry> encryptedEntriesLoaded,
//                                List<Entry> entriesLoaded,
//                                String masterPassword)
//            throws GeneralSecurityException {
//        Entry.instantiateKeySet(masterPassword);
//        for (Entry e : encryptedEntriesLoaded) {
//            entriesLoaded.add(e.decrypt());
//        }
//    }

    //EFFECTS: decrypts the entries from the JSONArray and adds it to a list of entries else
    // throws a GeneralSecurityException
    private void decryptEntries(JSONArray encryptedEntriesLoaded,
                                List<Entry> entriesLoaded,
                                String masterPassword) {
        Entry.instantiateKeySet(masterPassword);
        for (int i = 0; i < encryptedEntriesLoaded.length(); i++) {
            JSONObject entryJson = encryptedEntriesLoaded.getJSONObject(i);
            Entry e = new Entry(
                    entryJson.getString("name"),
                    entryJson.getString("username"),
                    new Password(entryJson.getString("password")),
                    entryJson.getString("url"),
                    entryJson.getString("notes")
            );
            entriesLoaded.add(e);
        }
    }

    //EFFECTS: parses record from JSON object and returns it
    private Record parseFile(ArrayList<Entry> entriesLoaded) {
        Record r = new Record();
        r.setEntries(entriesLoaded);
        return r;
    }

}
