package ui;

import me.gosimple.nbvcxz.resources.Generator;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static model.PasswordCreator.CharacterTypes;

import static ui.Input.CREATE;
import static ui.Input.CUSTOM;
import static ui.Input.LIST;
import static ui.Input.PASSPHRASE;
import static ui.Input.PASSWORD;
import static ui.Input.RANDOM;
import static ui.Input.VIEW;
import static ui.Input.SAVE;
import static ui.Input.LOAD;
import static ui.Input.EXIT;

public class PasswordSystem {
    private static final String JSON_STORE = "./data/workroom.json";
    private Record record;
    private Scanner scan;
    private PasswordCreator passwordCreator;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String masterPassword;

    //EFFECTS: initializes the required objects record, scan, passwordCreator, jsonWriter, jsonReader and
    // displays the user interface
    public void start() throws FileNotFoundException {
        record = new Record();
        scan = new Scanner(System.in);
        passwordCreator = PasswordCreator.getInstance();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        //userDisplay();
    }

    //EFFECTS: displays the user interface and waits for the user ot navigate through the system
    private void userDisplay() {
        boolean condition = false;
        do {
            System.out.println("Welcome to SecurePass- Password Management System!\n"
                    + "The following options can be used to navigate:\n"
                    + "Enter " + CREATE + " to create a new entry in the system.\n"
                    + "Enter " + VIEW + " to view an entry in the system.\n"
                    + "Enter " + LIST + " to view all the entries stored.\n"
                    + "Enter " + SAVE + " to save your entries in a file.\n"
                    + "Enter " + LOAD + " to load the file.\n"
                    + "Enter " + EXIT + " to exit the system.");
            condition = checkInput(scan.nextLine());
        } while (!condition);
    }

    //EFFECTS: processes the user input and performs the corresponding action based on the input
    private boolean checkInput(String input) {
        Input correspondingEnum = Input.findCorrespondingEnum(input);
        if (correspondingEnum == CREATE) {
            createEntry();
            System.out.println();
        } else if (correspondingEnum == VIEW) {
            viewEntry();
        } else if (correspondingEnum == LIST) {
            listEntries();
            System.out.println();
        } else if (correspondingEnum == SAVE) {
            saveFile();
        } else if (correspondingEnum == LOAD) {
            loadFile();
        } else if (correspondingEnum == EXIT) {
            System.out.println("Thank you for using SecurePass! Have a good day!\n\nRecord: ");
            return true;
        } else {
            System.out.println("Sorry, your input is invalid. Please try again.");
        }
        return false;
    }

//    private boolean checkInput(String input) {
//        switch (Input.findCorrespondingEnum(input)) {
//            case CREATE:
//                createEntry();
//                System.out.println();
//                break;
//            case VIEW:
//                viewEntry();
//                break;
//            case LIST:
//                listEntries();
//                System.out.println();
//                break;
//            case SAVE:
//                saveFile();
//                break;
//            case LOAD:
//                loadFile();
//                break;
//            case EXIT:
//                System.out.println("Thank you for using SecurePass! Have a good day!\n\nRecord: ");
//                //System.out.println("Record: ");
//                return true;
//            default:
//                System.out.println("Sorry, your input is invalid. Please try again.");
//                break;
//        } return false;
//    }

    //MODIFIES: this
    //EFFECTS: prompts the user to enter details for a new entry, creates an entry
    // and adds it to the record
    private void createEntry() {
        String name = takeInput("name");
        String username = takeInput("username");
        Password password = takePasswordInput();
        String url = takeInput("URL");
        String notes = takeInput("notes");

        Entry e = new Entry(name, username, password, url, notes);
        record.addEntry(e);
    }

    public void createEntryForGUI(String name, String username, Password password, String url, String notes) {
        Entry entry = new Entry(name, username, password, url, notes);
        record.addEntry(entry);
    }


    //REQUIRES: input should be of valid type
    //EFFECTS: prompts the user to input a value for the specified type and returns the user's input
    // as a string
    private String takeInput(String input) {
        System.out.println("Enter the " + input + " for this entry: ");
        return scan.nextLine();
    }

    //EFFECTS: prompts a user to choose between a custom password or a random password
    private Password takePasswordInput() {
        Password p = null;
        String input;
        Input i;
        do {
            System.out.println(
                    "Enter " + CUSTOM + " to create a custom password.\n" + "or " + RANDOM + " for a random password.");
            input = scan.nextLine();
            i = Input.findCorrespondingEnum(input);
            switch (i) {
                case CUSTOM:
                    System.out.println("Enter your desired password:");
                    String ps = scan.nextLine();
                    p = new Password(ps);
                    break;
                case RANDOM:
                    p = createRandomPassword();
                    break;
                default:
                    System.out.println("Sorry, your input is invalid. Please try again.");
                    break;
            }
        } while (i != CUSTOM && i != RANDOM);
        return p;
    }

    //EFFECTS: prompts the user to create a random password or passphrase and returns the password object
    // with a generated value
    private Password createRandomPassword() {
        System.out.println("Enter " + PASSPHRASE + " to create a passphrase.\n"
                + "Enter " + PASSWORD + " to create a password.");
        String input = scan.nextLine();
        String ps = null;
        Input i = Input.findCorrespondingEnum(input);
        if (i == PASSWORD) {
            ArrayList<Boolean> charTypesList = takeInputForCharTypes();
            System.out.println("Enter the number of characters you want in your password: ");
            int length = next();
            ps = passwordCreator.createPassword(charTypesList, length);
        } else if (i == PASSPHRASE) {
            System.out.println("Enter the numbers of words to be contained in the passphrase: ");
            int words = next();
            ps = passwordCreator.generatePassphrase("-", words);
        } else {
            System.out.println("Sorry, your input is invalid. Please try again.");
            return createRandomPassword();
        }
        return new Password(ps);
    }

    //EFFECTS: prompts the user to specify the character types for the password and returns
    // an ArrayList representing the user's choices
    private ArrayList<Boolean> takeInputForCharTypes() {
        ArrayList<Boolean> charTypesList = new ArrayList<>();
        System.out.println("Enter 'yes'/'no' if you want lowercase letters in your password: ");
        charTypesList.add(inputToBoolean());
        System.out.println("Enter 'yes'/'no' if you want uppercase letters in your password: ");
        charTypesList.add(inputToBoolean());
        System.out.println("Enter 'yes'/'no' if you want symbols in your password: ");
        charTypesList.add(inputToBoolean());
        System.out.println("Enter 'yes'/'no' if you want numbers in your password: ");
        charTypesList.add(inputToBoolean());

        if (allFalse(charTypesList)) {
            System.out.println("Please enter 'yes' for at least one character type.");
            charTypesList = takeInputForCharTypes();
        }
        return charTypesList;
    }

    //REQUIRES: list is a valid list of booleans
    //EFFECTS: checks if all values provided in the list are false and returns a boolean
    private boolean allFalse(ArrayList<Boolean> list) {
        for (Boolean b : list) {
            if (b) {
                return false;
            }
        }
        return true;
    }

    //EFFECTS: prompts the user to enter the index of the entry they want to view and displays
    // the detailed view of the entry if it exists
    private void viewEntry() {
        System.out.println("Enter the number of the entry you would like to view: \n");
        int index = next() - 1;
        try {
            String entry = record.viewEntry(index);
            System.out.println(entry);
        } catch (IndexOutOfBoundsException ie) {
            System.out.println("Entry number" + ++index + " does not exist.");
        }
    }

    //EFFECTS: displays a list of entries or a message if there are no entries
    private void listEntries() {
        if (record.getLengthOfEntries() == 0) {
            System.out.println("No entries have been added.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < record.getLengthOfEntries(); i++) {
                sb.append("-----------------------------------\n");
                sb.append("Your list of entries is as follows:\n");
                Entry e = record.getEntryAtIndex(i);
                sb.append(e.toString(i));
            }
            System.out.println(sb);
        }
    }

    //EFFECTS: reads an integer from the user's input and returns it
    private int next() {
        int input = scan.nextInt();
        scan.nextLine();
        return input;
    }

    //EFFECTS: reads the user's input and returns 'true' if 'yes' and 'false' if 'no'
    private boolean inputToBoolean() {
        String input = scan.nextLine();
        switch (input) {
            case "yes":
                return true;
            case "no":
                return false;
            default:
                System.out.println("Sorry, your input is invalid. Please try again.");
                inputToBoolean();
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: prompts the user for a master password, and then attempts to save the record to a JSON
    // file using the provided master password
    private void saveFile() {
        try {
            if (masterPassword == null) {
                System.out.println("Enter your master password: ");
                masterPassword = scan.nextLine();
            }
            jsonWriter.open();
            jsonWriter.write(record, masterPassword);
            jsonWriter.close();
            System.out.println("The file is saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: prompts the user for a master password and then attempts to load the record
    // from a JSON file using the master password, if an error occurs then it resets the master password
    // to null
    private void loadFile() {
        try {
            System.out.println("Enter your master password: ");
            masterPassword = scan.nextLine();
            record = jsonReader.read(masterPassword, JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            masterPassword = null;
        }
    }

    public void saveFileFromGUI(String masterPassword) {
        try {
            //System.out.println("Enter your master password: ");
            jsonWriter.open();
            jsonWriter.write(record, masterPassword);
            jsonWriter.close();
            //System.out.println("Saved file to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public void loadFileFromGUI(String masterPassword) {
        try {
            record = jsonReader.read(masterPassword, JSON_STORE);
            //System.out.println("Loaded file from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file :" + JSON_STORE);
        }
    }

    public Record getRecord() {
        return record;
    }

    public String generatePasswordForGUI(ArrayList<Boolean> characterTypesBoolean, int length) {
        ArrayList<CharacterTypes> ct = passwordCreator.addCharTypes(characterTypesBoolean);
        return createPassword(ct, length);
    }

    private String createPassword(ArrayList<CharacterTypes> ct, int length) {
        return passwordCreator.createRandomPassword(ct, length);
    }

    public String generatePassphraseForGUI(int words) {
        return Generator.generatePassphrase("-", words);
    }


}
