package com.desafio.chatgpt;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITestTest {

    @BeforeEach
    public void setup() throws UnirestException {
        // Mock da HttpResponse para simular a resposta do Unirest
        HttpResponse<String> mockedResponse = Mockito.mock(HttpResponse.class);
        when(mockedResponse.getStatus()).thenReturn(200);
        when(mockedResponse.getBody()).thenReturn("{\"method\":\"GET\", \"status\":\"success\"}");

        // Mock do Unirest.get() para retornar a resposta mockada
        Mockito.mockStatic(Unirest.class);
        when(Unirest.get("https://dummyjson.com/test")).thenReturn(mockedResponse);
    }

    @Test
    public void testFetchData() throws UnirestException, JSONException {
        com.desafio.chatgpt.Test apiTest = new com.desafio.chatgpt.Test();
        JSONObject result = apiTest.fetchData();

        assertEquals("GET", result.getString("method"));
        assertEquals("success", result.getString("status"));
    }
}
