package com.desafio.chatgpt;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class Test {

    public JSONObject fetchData() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://dummyjson.com/test")
                .asString();

        if (response.getStatus() == 200) {
            return new JSONObject(response.getBody());
        } else {
            throw new UnirestException("Falha ao obter a resposta: " + response.getStatusText());
        }
    }
}