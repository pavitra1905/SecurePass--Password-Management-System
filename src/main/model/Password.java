package model;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.resources.Feedback;
import me.gosimple.nbvcxz.scoring.Result;

//Represents a password with the passwordString password, result field and feedback field.
//The latter two are provided by the Nbvcxz library and are used to calculate the password
//strength and give potential feedback on bad passwords.

public class Password {
    private String passwordString;
    private Result result;
    private Feedback feedback;

    //REQUIRES: password should not be null and not an empty string
    //EFFECTS: creates a password object and sets the password field to the given parameter
    // and creates a temporary Nbvcxz object to instantiate the result and feedback fields
    // depending on the parameter
    public Password(String passwordString) {
        Nbvcxz nbvcxz = new Nbvcxz();

        this.passwordString = passwordString;
        result = nbvcxz.estimate(passwordString);
        feedback = result.getFeedback();
    }

    //EFFECTS: returns the result
    public Result getResult() {
        return result;
    }

    //REQUIRES: result is not null
    //EFFECTS: returns the score (between 1 and 4) of the password created by Nbvcxz
    public int getScore() {
        return result.getBasicScore();
    }

//    public int getEntropyScore() {
//        double entropy = result.getEntropy();
//        if (entropy <= 10) {
//            return 0;
//        } else if (entropy <= 32) {
//            return 1;
//        } else if (entropy <= 64) {
//            return 2;
//        } else {
//            return entropy <= 70 ? 3 : 4;
//        }
//    }

    //EFFECTS: returns the passwordString
    public String getPasswordString() {
        return passwordString;
    }

    //EFFECTS: returns the feedback
    public Feedback getFeedback() {
        return feedback;
    }
}

