package com.example.mindscribe;

import static junit.framework.TestCase.assertEquals;

import com.google.gson.Gson;

import org.junit.Test;

public class APIResponseTest {
    @Test
    public void getBodyTest() {
        String json = "{ \"body\": \"this is a body\" }";
        Gson g = new Gson();
        APIResponse response = g.fromJson(json, APIResponse.class);

        assertEquals(response.getBody(), "this is a body");
    }
}
