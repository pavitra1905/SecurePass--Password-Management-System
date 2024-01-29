package model;

import model.Password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
    private Password testPassword;

    @BeforeEach
    void runBefore() {
        testPassword = new Password("guest");
    }

    @Test
    public void testConstructor() {
        assertEquals(testPassword.getPasswordString(), "guest");
    }

    @Test
    public void testGetScoreForWeakPassword() {
        assertEquals(1, testPassword.getScore());
    }

    @Test
    public void testGetScoreForStrongPassword() {
        testPassword = new Password("kl363reb8642misp12iifm937839392u2fj393nf");
        assertEquals(4, testPassword.getScore());
    }
}
