package ca.jrvs.apps.practice;

import org.junit.Test;
import static org.junit.Assert.*;

//This test class tests some values for our RegexExc Implementation
public class RegexExcImpTest {

    @Test
    public void matchJpeg() {
        RegexExcImp test = new RegexExcImp();
        assertTrue(test.matchJpeg("test.jpeg"));
        assertTrue(test.matchJpeg("test.jpg"));

        assertFalse(test.matchJpeg(".jpeg"));
        assertFalse(test.matchJpeg("jpeg"));
        assertFalse(test.matchJpeg(".jpg"));
    }

    @Test
    public void matchIp() {
        RegexExcImp test = new RegexExcImp();
        assertTrue(test.matchIp("192.168.1.1"));
        assertTrue(test.matchIp("255.255.255.0"));

        assertFalse(test.matchIp("192.168"));
        assertFalse(test.matchIp("jpg"));
        assertFalse(test.matchIp("this should not pass.123"));
    }

    @Test
    public void isEmptyLine() {
        RegexExcImp test = new RegexExcImp();
        assertTrue(test.isEmptyLine("  "));
        assertTrue(test.isEmptyLine(""));

        assertFalse(test.isEmptyLine("this should not pass  "));
    }
}