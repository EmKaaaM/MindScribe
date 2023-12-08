package com.example.mindscribe;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JournalEntryTest {
    JournalEntry testEntry;

    @Before
    public void init() {
        testEntry = new JournalEntry(2024, 6, 29, "entry text",
                1, "happy", "school");
    }

    @Test
    public void constructorTest() {
        assertEquals(testEntry.getYear(), 2024);
        assertEquals(testEntry.getMonth(), 6);
        assertEquals(testEntry.getDayOfMonth(), 29);
        assertEquals(testEntry.getEntry(), "entry text");
        assertEquals(testEntry.getUserID(), 1);
        assertEquals(testEntry.getMood(), "happy");
        assertEquals(testEntry.getKeywords(), "school");
    }

    @Test
    public void yearSetterGetterTest() {
        testEntry.setYear(200);
        assertEquals(testEntry.getYear(), 200);
    }

    @Test
    public void monthSetterGetterTest() {
        testEntry.setMonth(2);
        assertEquals(testEntry.getMonth(), 2);
    }

    @Test
    public void daySetterGetterTest() {
        testEntry.setDayOfMonth(15);
        assertEquals(testEntry.getDayOfMonth(), 15);
    }

    @Test
    public void entrySetterGetterTest() {
        testEntry.setEntry("This is a new entry text!");
        assertEquals(testEntry.getEntry(), "This is a new entry text!");
    }

    @Test
    public void userIdSetterGetterTest() {
        testEntry.setUserID(56);
        assertEquals(testEntry.getUserID(), 56);
    }

    @Test
    public void moodSetterGetterTest() {
        testEntry.setMood("Sad");
        assertEquals(testEntry.getMood(), "Sad");
    }

    @Test
    public void keywordsSetterGetterTest() {
        testEntry.setKeywords("Work, Romance");
        assertEquals(testEntry.getKeywords(), "Work, Romance");
    }
}
