package ui;

public enum Input {
    CREATE,
    LIST,
    EXIT,
    CUSTOM,
    RANDOM,
    PASSPHRASE,
    PASSWORD,
    VIEW,
    SAVE,
    LOAD,
    DEFAULT;

    //need to add save and load methods later

    Input() {
    }

    //EFFECTS: returns enum corresponding to input or Input.DEFAULT if enum doesn't exist
    public static Input findCorrespondingEnum(String input) {
        Input i;
        try {
            i = Input.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            i = DEFAULT;
        }
        return i;
    }

    //EFFECTS: converts super to String and then to lowercase and returns it
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}