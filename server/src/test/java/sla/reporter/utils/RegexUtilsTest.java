package sla.reporter.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests regex.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public class RegexUtilsTest {

    /**
     * Ensures {@link RegexUtils#isEmailValid(String)} works as expexted.
     */
    @Test
    public void validateEmailRegex() {
        final String CorrectEmail = "jonh@example.com";
        final String WrongEmail1 = "jonh@com";
        final String WrongEmail2 = "jonh.test";
        final String WrongEmail3 = "jonh.test@example";
        final String WrongEmail4 = "jonh@i@example.com";
        final String WrongEmail5 = "jonh test@example.com";
        final String WrongEmail6 = "jonh@exam ple.com";
        final String WrongEmail7 = "jonh@example.c om";
        final String WrongEmail8 = "jonh@example.com ";
        final String WrongEmail9 = " jonh@example.com";
        Assert.assertTrue("The correct email should return true", RegexUtils.isEmailValid(CorrectEmail));
        Assert.assertFalse("The wrong format email 1 should return false", RegexUtils.isEmailValid(WrongEmail1));
        Assert.assertFalse("The wrong format email 2 should return false", RegexUtils.isEmailValid(WrongEmail2));
        Assert.assertFalse("The wrong format email 3 should return false", RegexUtils.isEmailValid(WrongEmail3));
        Assert.assertFalse("The wrong format email 4 should return false", RegexUtils.isEmailValid(WrongEmail4));
        Assert.assertFalse("The wrong format email 5 should return false", RegexUtils.isEmailValid(WrongEmail5));
        Assert.assertFalse("The wrong format email 6 should return false", RegexUtils.isEmailValid(WrongEmail6));
        Assert.assertFalse("The wrong format email 7 should return false", RegexUtils.isEmailValid(WrongEmail7));
        Assert.assertFalse("The wrong format email 8 should return false", RegexUtils.isEmailValid(WrongEmail8));
        Assert.assertFalse("The wrong format email 9 should return false", RegexUtils.isEmailValid(WrongEmail9));
    }
}
