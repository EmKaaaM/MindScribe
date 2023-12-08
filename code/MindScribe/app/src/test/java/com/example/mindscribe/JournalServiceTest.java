package com.example.mindscribe;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JournalServiceTest {
    @Mock
    public Context context;

    @Mock
    public SharedPreferences sharedPreferences;

    public JournalService testService;

    public JournalEntry testEntry;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        Mockito.when(sharedPreferences.getString("jwt_token", null)).thenReturn("token");
        testEntry = new JournalEntry(2024, 6, 29, "entry text",
                1, "happy", "school");

        testService = new JournalService(context, "http://api.com");
    }

    @Test
    public void getTokenTest() {
        assertEquals(testService.getToken(), "token");
    }

    @Test
    public void createJournalEntryThrowsIfTokenNull() {
        Mockito.when(testService.getToken()).thenReturn(null);

        Assert.assertThrows(IllegalStateException.class, () -> testService.createJournalEntry(testEntry));
    }
}
