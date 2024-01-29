package ui;

import model.Entry;
import model.EventLog;
import model.Record;
import model.Password;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// Use of form file approved by Paul Carter
// according to https://piazza.com/class/lly08e6z2hzwn/post/1340
public class GUI extends JFrame implements ActionListener {

    private JPanel cardPanel;
    private JPanel introMenu;
    private JButton createButton;
    private JButton loadFromIntroButton;
    private JPanel createFieldMenu;
    private JTextField nameTextField;
    private JTextField userNameTextField;
    private JTextField urlTextField;
    private JTextField notesTextField;
    private JButton nextButton;
    private JPanel chooseCustomOrRandom;
    private JRadioButton customPasswordRadioButton;
    private JRadioButton generatePasswordRadioButton;
    private JPanel customPassword;
    private JPasswordField customPasswordField;
    private JButton nextButtonFromCustomPassword;
    private JPanel choosePasswordOrPassphrase;
    private JRadioButton passphraseRadioButton;
    private JRadioButton passwordRadioButton;
    private JPanel passwordSpecifications;
    private JTextField passwordLengthTextField;
    private JButton nextButton2;
    private JRadioButton lowercaseNo;
    private JRadioButton uppercaseNo;
    private JRadioButton numbersNo;
    private JRadioButton symbolsNo;
    private JRadioButton lowercaseYes;
    private JRadioButton uppercaseYes;
    private JRadioButton numbersYes;
    private JRadioButton symbolsYes;
    private JPanel passphraseSpecifications;
    private JTextField passphraseLengthTextField;
    private JButton nextButton3;
    private JPanel mainMenu;
    private JButton createANewEntryButton;
    private JButton listAllEntriesButton;
    private JButton saveEntriesToFileButton;
    private JButton loadEntriesFromFileButton;
    private JButton exitButton;
    private JList list1;
    private JPanel listEntries;
    private JPanel saveEntries;
    private JPasswordField loadPasswordTextField;
    private JButton loadButton1;
    private JButton backButton;
    private JPasswordField savePasswordTextField;
    private JButton saveButton;
    private JButton backButton1;
    private JPanel loadEntries;
    //private JButton deleteAnEntryButton;
    //private JTextField deleteEntryTextField;
    //private JButton deleteButton;
    //private JPanel deleteEntry;
    private JTable table1;
    private JRadioButton scoreButton1;
    private JRadioButton scoreButton2;
    private JRadioButton scoreButton3;
    private JRadioButton scoreButton4;
    private JRadioButton scoreButtonAll;
    private JRadioButton scoreButton0;
    private JButton backButton2;

    private PasswordSystem passwordSystem;
    private CardLayout cl;
    private boolean loadFromIntro;
    private ArrayList<ButtonGroup> buttonGroups;

    private static final String CREATE_BUTTON = "CREATE BUTTON";
    private static final String LOAD_BUTTON = "LOAD BUTTON";
    private static final String LOAD_FROM_INTRO_BUTTON = "LOAD_FROM_INTRO_BUTTON";
    private static final String GO_TO_SAVE_BUTTON = "GO TO SAVE BUTTON";
    private static final String SAVE_BUTTON = "SAVE BUTTON";
    private static final String NEXT_BUTTON = "NEXT BUTTON";
    private static final String GENERATE_PASSWORD_BUTTON = "GENERATE_PASSWORD_BUTTON ";
    private static final String CUSTOM_PASSWORD_BUTTON = "CUSTOM_PASSWORD_BUTTON ";
    private static final String PASSWORD_BUTTON = "PASSWORD_BUTTON ";
    private static final String PASSPHRASE_BUTTON = "PASSPHRASE_BUTTON";
    private static final String BUTTON_TO_MAIN_MENU_FROM_PASSWORD = "BUTTON_TO_MAIN_MENU_FROM_PASSWORD";
    private static final String BUTTON_TO_MAIN_MENU_FROM_PASSPHRASE = "BUTTON_TO_MAIN_MENU_FROM_PASSPHRASE";
    private static final String BUTTON_TO_MAIN_MENU_FROM_CUSTOM_PASSWORD = "BUTTON TO MAIN MENU FROM CUSTOM PASSWORD";
    private static final String BACK_FROM_LOAD_BUTTON = "BACK_FROM_LOAD_BUTTON";
    private static final String BACK_BUTTON = "BACK_BUTTON";
    private static final String EXIT_BUTTON = "EXIT BUTTON";
    private static final String LOAD_ENTRIES_BUTTON = "LOAD ENTRIES BUTTON";
    private static final String LIST_ENTRIES = "LIST ENTRIES";
    //private static final String DELETE_BUTTON = "DELETE BUTTON";
    //private static final String GO_TO_DELETE_BUTTON = "GO_TO_DELETE_BUTTON";
    private static final String A0_BUTTON = "A0 BUTTON";
    private static final String A1_BUTTON = "A1 BUTTON";
    private static final String A2_BUTTON = "A2 BUTTON";
    private static final String A3_BUTTON = "A3 BUTTON";
    private static final String A4_BUTTON = "A4 BUTTON";
    private static final String ALL_BUTTON = "ALL BUTTON";
    private static final String BACK_FROM_LIST_BUTTON = "BACK FROM LIST BUTTON";

    //EFFECTS: constructor for GUI class
    public GUI() throws FileNotFoundException {
        super("Password Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener();
        setSize(new Dimension((int) (600 * 1.5), (int) (500 * 1.5)));
        setContentPane(cardPanel);

        showSplashScreen();
        setVisible(true);

        passwordSystem = new PasswordSystem();
        passwordSystem.start();

        setupCardLayout();
        addActionToButtons();
        populateButtonGroups();
    }

    //MODIFIES: this
    //EFFECTS: adds a window listener which prints the log of events
    private void addWindowListener() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog();
            }
        });
    }

    //EFFECTS: prints the log of events to the console after exiting the program
    private void printLog() {
        passwordSystem.getRecord().printLog(EventLog.getInstance());
        System.exit(0);
    }

    //EFFECTS: shows a splash screen on start-up
    //GIF was downloaded from https://1st-it.com/it-security-audit-2/
    private void showSplashScreen() {
        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", new ImageIcon("data/passwordsystem_image.gif"), SwingConstants.CENTER));
        window.setSize(new Dimension((int) (600 * 1.5), (int) (500 * 1.5)));
        window.setVisible(true);
        try {
            Thread.sleep(5050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }

    //MODIFIES: buttonGroups
    //EFFECTS: initializes and adds 5 button groups to buttonGroups
    private void populateButtonGroups() {
        buttonGroups = new ArrayList<>();
        buttonGroups.add(new ButtonGroup());
        buttonGroups.add(new ButtonGroup());
        buttonGroups.add(new ButtonGroup());
        buttonGroups.add(new ButtonGroup());
        buttonGroups.add(new ButtonGroup());
    }

    //MODIFIES: cl
    //EFFECTS: initializes cl to be the layout of the main panel and displays the
    // first (intra) panel
    private void setupCardLayout() {
        cl = (CardLayout) cardPanel.getLayout();
        cl.first(cardPanel);
    }

    //MODIFIES: listAllEntriesButton, generatePasswordRadioButton, customPasswordRadioButton,
    // passphraseRadioButton, passwordRadioButton, saveEntriesToFileButton, saveButton, exitButton
    //EFFECTS: adds a unique action command to the specified buttons
    public void addActionToButtons() {
        activateCreateButtons();
        activateLoadButtons();
        activateNextButtons();
        activateBackButtons();
        activateScoreButtons();
        activate(listAllEntriesButton, LIST_ENTRIES);
        activate(generatePasswordRadioButton, GENERATE_PASSWORD_BUTTON);
        activate(customPasswordRadioButton, CUSTOM_PASSWORD_BUTTON);
        activate(passphraseRadioButton, PASSPHRASE_BUTTON);
        activate(passwordRadioButton, PASSWORD_BUTTON);
        activate(saveEntriesToFileButton, GO_TO_SAVE_BUTTON);
        activate(saveButton, SAVE_BUTTON);
        //activate(deleteButton, DELETE_BUTTON);
        //activate(deleteAnEntryButton, GO_TO_DELETE_BUTTON);
        activate(exitButton, EXIT_BUTTON);
    }

    //MODIFIES: scoreButton0, scoreButton1, scoreButton2, scoreButton3, scoreButton4, scoreButtonAlL
    //EFFECTS: adds a unique action command to the specified buttons
    private void activateScoreButtons() {
        activate(scoreButton0, A0_BUTTON);
        activate(scoreButton1, A1_BUTTON);
        activate(scoreButton2, A2_BUTTON);
        activate(scoreButton3, A3_BUTTON);
        activate(scoreButton4, A4_BUTTON);
        activate(scoreButtonAll, ALL_BUTTON);
    }

    //MODIFIES: backButton, backButton1, backButton2
    //EFFECTS: adds a unique action command to the specified buttons
    private void activateBackButtons() {
        activate(backButton, BACK_FROM_LOAD_BUTTON);
        activate(backButton1, BACK_BUTTON);
        activate(backButton2, BACK_FROM_LIST_BUTTON);
    }

    //MODIFIES: nextButton, nextButton2, nextButton3, nextButtonFromCustomPassword
    //EFFECTS: adds a unique action command to the specified buttons
    private void activateNextButtons() {
        activate(nextButton, NEXT_BUTTON);
        activate(nextButton2, BUTTON_TO_MAIN_MENU_FROM_PASSWORD);
        activate(nextButton3, BUTTON_TO_MAIN_MENU_FROM_PASSPHRASE);
        activate(nextButtonFromCustomPassword, BUTTON_TO_MAIN_MENU_FROM_CUSTOM_PASSWORD);
    }

    //MODIFIES: loadFromIntroButton, loadButton1, loadEntriesFromFileButton
    //EFFECTS: adds a unique action command to the specified buttons
    private void activateLoadButtons() {
        activate(loadFromIntroButton, LOAD_FROM_INTRO_BUTTON);
        activate(loadButton1, LOAD_ENTRIES_BUTTON);
        activate(loadEntriesFromFileButton, LOAD_BUTTON);
    }

    //MODIFIES: createButton, createANewEntryButton
    //EFFECTS: adds a unique action command to the specified buttons
    private void activateCreateButtons() {
        activate(createButton, CREATE_BUTTON);
        activate(createANewEntryButton, CREATE_BUTTON);
    }

    //REQUIRES: button and actionCommand is not null; actionCommand is a unique string
    //MODIFIES: button
    //EFFECTS: adds an action listener to  radio button and sets its action command
    public void activate(JRadioButton button, String actionCommand) {
        button.addActionListener(this);
        button.setActionCommand(actionCommand);
    }

    //REQUIRES: button and actionCommand is not null; actionCommand is a unique string
    //MODIFIES: button
    //EFFECTS: adds an action listener to button and sets its action command
    public void activate(JButton button, String actionCommand) {
        button.addActionListener(this);
        button.setActionCommand(actionCommand);
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case CREATE_BUTTON:
                cl.show(cardPanel, "createFieldMenu");
                break;
            case LOAD_BUTTON:
            case LOAD_FROM_INTRO_BUTTON:
            case LOAD_ENTRIES_BUTTON: {
                actionPerformedLoad(actionEvent);
                break;
            }
            case NEXT_BUTTON:
            case BUTTON_TO_MAIN_MENU_FROM_PASSPHRASE:
            case BUTTON_TO_MAIN_MENU_FROM_PASSWORD:
            case BUTTON_TO_MAIN_MENU_FROM_CUSTOM_PASSWORD: {
                actionPerformedNext(actionEvent);
                break;
            }
            default:
                actionPerformedTwo(actionEvent);
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedTwo(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case PASSWORD_BUTTON:
            case PASSPHRASE_BUTTON: {
                actionPerformedGenerate(actionEvent);
                break;
            }
            case A0_BUTTON:
            case A1_BUTTON:
            case A2_BUTTON:
            case A3_BUTTON:
            case A4_BUTTON:
            case ALL_BUTTON: {
                actionPerformedFilterTable(actionEvent);
                break;
            }
            default:
                actionPerformedThree(actionEvent);
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedThree(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case GO_TO_SAVE_BUTTON:
            case SAVE_BUTTON: {
                actionPerformedSave(actionEvent);
                break;
            }
//            case GO_TO_DELETE_BUTTON:
//            case DELETE_BUTTON: {
//                actionPerformedDelete(actionEvent);
//                break;
//            }
            case LIST_ENTRIES:
                listEntries(passwordSystem.getRecord().getEntries());
                cl.show(cardPanel, "listEntries");
                break;
            case EXIT_BUTTON:
                printLog();
                System.exit(0);
                break;
            default:
                actionPerformedFour(actionEvent);
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedFour(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case CUSTOM_PASSWORD_BUTTON:
            case GENERATE_PASSWORD_BUTTON: {
                actionPerformedRandom(actionEvent);
                break;
            }
            case BACK_BUTTON:
            case BACK_FROM_LIST_BUTTON:
            case BACK_FROM_LOAD_BUTTON: {
                actionPerformedBack(actionEvent);
                break;
            }
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedSave(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case SAVE_BUTTON:
                save();
                clearTextField(savePasswordTextField);
                cl.show(cardPanel, "mainMenu");
                break;
            case GO_TO_SAVE_BUTTON:
                cl.show(cardPanel, "saveEntries");
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedBack(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case BACK_BUTTON:
                clearTextField(savePasswordTextField);
                cl.show(cardPanel, "mainMenu");
                break;
            case BACK_FROM_LOAD_BUTTON:
                clearTextField(loadPasswordTextField);
                if (loadFromIntro) {
                    cl.show(cardPanel, "introMenu");
                } else {
                    cl.show(cardPanel, "mainMenu");
                }
                break;
            case BACK_FROM_LIST_BUTTON:
                filterTable(5);
                clearRadioButtonGroup(buttonGroups.get(4));
                cl.show(cardPanel, "mainMenu");
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedFilterTable(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case A0_BUTTON:
                filterTable(0);
                break;
            case A1_BUTTON:
                filterTable(1);
                break;
            case A2_BUTTON:
                filterTable(2);
                break;
            case A3_BUTTON:
                filterTable(3);
                break;
            case A4_BUTTON:
                filterTable(4);
                break;
            case ALL_BUTTON:
                filterTable(5);
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedGenerate(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case PASSWORD_BUTTON:
                cl.show(cardPanel, "passwordSpecifications");
                makeRadioButtonsGroup();
                clearRadioButton(passwordRadioButton);
                break;
            case PASSPHRASE_BUTTON:
                cl.show(cardPanel, "passphraseSpecifications");
                clearRadioButton(passphraseRadioButton);
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedRandom(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case CUSTOM_PASSWORD_BUTTON:
                cl.show(cardPanel, "customPassword");
                clearRadioButton(customPasswordRadioButton);
                break;
            case GENERATE_PASSWORD_BUTTON:
                cl.show(cardPanel, "choosePasswordOrPassphrase");
                clearRadioButton(generatePasswordRadioButton);
                break;
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedNext(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case NEXT_BUTTON:
                cl.show(cardPanel, "chooseCustomOrRandom");
                break;
            case BUTTON_TO_MAIN_MENU_FROM_PASSPHRASE:
                callCreateEntry(1);
                clearRandomPassphraseMenu();
                cl.show(cardPanel, "mainMenu");
                break;
            case BUTTON_TO_MAIN_MENU_FROM_PASSWORD:
                callCreateEntry(2);
                clearRandomPasswordMenu();
                cl.show(cardPanel, "mainMenu");
                break;
            case BUTTON_TO_MAIN_MENU_FROM_CUSTOM_PASSWORD:
                callCreateEntry(3);
                clearCustomPasswordMenu();
                cl.show(cardPanel, "mainMenu");
        }
    }

    //MODIFIES: cl
    //EFFECTS: reacts to different button presses on the GUI
    private void actionPerformedLoad(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case LOAD_BUTTON:
                cl.show(cardPanel, "loadEntries");
                loadFromIntro = false;
                break;
            case LOAD_FROM_INTRO_BUTTON:
                cl.show(cardPanel, "loadEntries");
                loadFromIntro = true;
                break;
            case LOAD_ENTRIES_BUTTON:
                load();
                clearTextField(loadPasswordTextField);
                cl.show(cardPanel, "mainMenu");
                break;
        }
    }

    //REQUIRES: 0 < i < 5
    //EFFECTS: filters the table to show passwords which have a password score from  0 to 4 or all passwords
    private void filterTable(int i) {
        Record record = passwordSystem.getRecord();
        ArrayList<Entry> entries = record.getEntries();
        if (i == 5) {
            listEntries(entries);
        } else {
            ArrayList<Entry> filteredEntries = new ArrayList<>();
            for (Entry e : entries) {
                if (e.getPassword().getScore() == i) {
                    filteredEntries.add(e);
                }
            }
            listEntries(filteredEntries);
        }
    }

    //REQUIRES: entries is not null
    //MODIFIES: table1
    //EFFECTS: adds all the entries in entries to the JTable and updates the GUI
    private void listEntries(ArrayList<Entry> entries) {
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table1.setModel(tableModel);

        tableModel.addColumn("Name");
        tableModel.addColumn("Username");
        tableModel.addColumn("Password");
        tableModel.addColumn("URL");
        tableModel.addColumn("Notes");

        tableModel.addRow(new Object[]{"Name","Username","Password","URL","Notes"});

        fillTableWithEntries(tableModel, entries);
        makePasswordScoreRadioButtons();
    }

    //MODIFIES: buttons
    //EFFECTS: adds all the password score buttons to the same button group
    private void makePasswordScoreRadioButtons() {
        ArrayList<JRadioButton> buttons = new ArrayList<>();
        buttons.add(scoreButton1);
        buttons.add(scoreButton2);
        buttons.add(scoreButton3);
        buttons.add(scoreButton4);
        buttons.add(scoreButtonAll);
        makePairOfRadioButtons(buttons, buttonGroups.get(4));
    }

    //REQUIRES: tableModel and entries are not null
    //MODIFIES: tableModel
    //EFFECTS: populates tableModel with the data from entries
    private void fillTableWithEntries(DefaultTableModel tableModel, ArrayList<Entry> entries) {
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            String[] entryData = new String[]{e.getName(), e.getUsername(), e.getPassword().getPasswordString(),
                    e.getUrl(), e.getNotes()};
            tableModel.addRow(entryData);
        }
    }

    //REQUIRES: masterPassword is not an empty string
    //MODIFIES: passwordManager
    //EFFECTS: saves the entries to file using the master password entered by the user
    private void save() {
        String masterPassword = new String(savePasswordTextField.getPassword());
        passwordSystem.saveFileFromGUI(masterPassword);
    }

    //REQUIRES: masterPassword is not an empty string
    //MODIFIES: passwordManager
    //EFFECTS: loads the entries from file using the master password entered by the user
    private void load() {
        String masterPassword = new String(loadPasswordTextField.getPassword());
        passwordSystem.loadFileFromGUI(masterPassword);
    }

    //REQUIRES: 1 <= passwordType <= 3
    //MODIFIES: passwordManager
    //EFFECTS: gets user input and creates an entry
    private void callCreateEntry(int passwordType) {
        String name = nameTextField.getText();
        String username = userNameTextField.getText();
        String url = urlTextField.getText();
        String notes = notesTextField.getText();
        Password password;
        if (passwordType == 3) {
            password = new Password(new String(customPasswordField.getPassword()));
        } else {
            password = getPassword(passwordType);
        }
        passwordSystem.createEntryForGUI(name, username, password, url, notes);

        clearCreateFieldMenu();
    }

    //REQUIRES: 1 <= passwordType <= 2
    //EFFECTS: returns a random password or passphrase depending on the choice selected by the user
    private Password getPassword(int passwordType) {
        String passwordText = null;
        if (passwordType == 1) {
            passwordText =
                    passwordSystem.generatePassphraseForGUI(Integer.parseInt(passphraseLengthTextField.getText()));
        } else {
            ArrayList<Boolean> characterTypesBoolean = new ArrayList<>();
            characterTypesBoolean.add(returnBooleanValue(lowercaseYes));
            characterTypesBoolean.add(returnBooleanValue(uppercaseYes));
            characterTypesBoolean.add(returnBooleanValue(numbersYes));
            characterTypesBoolean.add(returnBooleanValue(symbolsYes));
            passwordText = passwordSystem.generatePasswordForGUI(characterTypesBoolean,
                    Integer.parseInt(passwordLengthTextField.getText()));
        }
        return new Password(passwordText);
    }

    //REQUIRES: button is not null
    //EFFECTS: returns true if button is selected
    private boolean returnBooleanValue(JRadioButton button) {
        return button.isSelected();
    }

    //MODIFIES: nameTextField, userNameTextField, urlTextField, notesTextField
    //EFFECTS: clears the specified text fields
    private void clearCreateFieldMenu() {
        clearTextField(nameTextField);
        clearTextField(userNameTextField);
        clearTextField(urlTextField);
        clearTextField(notesTextField);
    }

    //MODIFIES: customPasswordField
    //EFFECTS: clears the specified text fields
    private void clearCustomPasswordMenu() {
        clearTextField(customPasswordField);
    }

    //MODIFIES: passwordLengthTextField, buttonsGroup.get(0), buttonsGroup.get(1), buttonsGroup.get(2),
    // buttonsGroup.get(3)
    //EFFECTS: clears the specified text fields and radio button groups
    private void clearRandomPasswordMenu() {
        clearTextField(passwordLengthTextField);
        clearRadioButtonGroup(buttonGroups.get(0));
        clearRadioButtonGroup(buttonGroups.get(1));
        clearRadioButtonGroup(buttonGroups.get(2));
        clearRadioButtonGroup(buttonGroups.get(3));
    }

    //MODIFIES: passphraseLengthTextField
    //EFFECTS: sets the text inside textField to be an empty string
    private void clearRandomPassphraseMenu() {
        clearTextField(passphraseLengthTextField);
    }

    //REQUIRES: radioButton is not null
    //MODIFIES: radioButton
    //EFFECTS: unselects radioButton
    private void clearRadioButton(JRadioButton radioButton) {
        radioButton.setSelected(false);
    }

    //REQUIRES: buttonGroup is not null
    //MODIFIES: buttonGroup
    //EFFECTS: unselects all JButtons in buttonGroup
    private void clearRadioButtonGroup(ButtonGroup buttonGroup) {
        buttonGroup.clearSelection();
    }

    //REQUIRES: textField is not null
    //MODIFIES: textField
    //EFFECTS: sets the text inside textField to be an empty string
    private void clearTextField(JTextField textField) {
        textField.setText("");
    }

    //MODIFIES: lowercaseNo, lowercaseYes, uppercaseNo, uppercaseYes, numbersNo, numbersYes, symbolsNo, symbolsYes
    //EFFECTS: makes pair radio buttons between the similar buttons
    private void makeRadioButtonsGroup() {
        makePairOfRadioButtons(lowercaseNo, lowercaseYes, buttonGroups.get(0));
        makePairOfRadioButtons(uppercaseNo, uppercaseYes, buttonGroups.get(1));
        makePairOfRadioButtons(numbersNo, numbersYes, buttonGroups.get(2));
        makePairOfRadioButtons(symbolsNo, symbolsYes, buttonGroups.get(3));
    }

    //REQUIRES: buttonA, buttonB, and group are not null
    //MODIFIES: group
    //EFFECTS: adds buttonA and buttonB to group
    private void makePairOfRadioButtons(JRadioButton buttonA, JRadioButton buttonB, ButtonGroup group) {
        group.add(buttonA);
        group.add(buttonB);
    }

    //REQUIRES: buttons, group
    //MODIFIES: group
    //EFFECTS: adds all JRadioButtons in buttons to group
    private void makePairOfRadioButtons(ArrayList<JRadioButton> buttons, ButtonGroup group) {
        for (JRadioButton button : buttons) {
            group.add(button);
        }
    }
}
