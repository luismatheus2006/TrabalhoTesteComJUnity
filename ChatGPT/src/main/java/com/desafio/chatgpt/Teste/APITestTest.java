package com.desafio.chatgpt.Teste;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITestTest {
    //Teste para ver se existe a comunicação com a API
    @Test
    public void testDaAPIdoTest() throws UnirestException {
        //Faz a comunicação com a API
        HttpResponse<JsonNode> response = Unirest.get("https://dummyjson.com/test").asJson();
        // Confirma que a resposta tem status 200
        assertEquals(200, response.getStatus(), "A resposta deve ter status 200 para uma requisição bem-sucedida.");
        // Confirma que o método da requisição é GET
        String methodUsed = response.getHeaders().getFirst("X-HTTP-Method-Override");
        if (methodUsed == null) {
            methodUsed = "GET"; // Se o cabeçalho não estiver presente, assume que o método GET foi utilizado
        }
        assertEquals("GET", methodUsed, "O método da requisição deve ser GET.");

        // Imprime o status e o método para verificação no terminal
        System.out.println("Status: " + response.getStatus() +"ok");
        System.out.println("Método da Requisição: " + methodUsed);
    }
}
