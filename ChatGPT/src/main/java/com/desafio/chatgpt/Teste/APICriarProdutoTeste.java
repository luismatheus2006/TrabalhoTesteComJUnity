package com.desafio.chatgpt.Teste;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class APICriarProdutoTeste {
    @Test
    public void TestarCriaçãoDeProdutos() {
        try {
            //Comunica com a API
            HttpResponse<String> response = Unirest.post("https://dummyjson.com/products/add")
                    //Informações do produto que será Criado

                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "    \"title\": \"Perfume Oil\",\n" +
                            "    \"description\": \"Mega Discount, Impression of A...\",\n" +
                            "    \"price\": 13,\n" +
                            "    \"discountPercentage\": 8.4,\n" +
                            "    \"rating\": 4.26,\n" +
                            "    \"stock\": 65,\n" +
                            "    \"brand\": \"Impression of Acqua Di Gio\",\n" +
                            "    \"category\": \"fragrances\",\n" +
                            "    \"thumbnail\": \"https://i.dummyjson.com/data/products/11/thumnail.jpg\"\n" +
                            "}")
                    .asString();

            assertNotNull(response, "A resposta não deve ser nula.");
            //Certifica que o Status seja 201
            assertEquals(201, response.getStatus(), "O código de status deve ser 201 (Created).");

            // Converte a resposta em JSON para facilitar o manuseio
            JSONObject jsonResponse = new JSONObject(response.getBody());
            assertNotNull(jsonResponse, "A resposta JSON não deve ser nula.");

            // Verifica se a resposta contém a chave "title" com o valor do título do produto adicionado
            assertTrue(jsonResponse.has("title"), "A resposta deve conter o título do produto.");
            assertEquals("Perfume Oil", jsonResponse.getString("title"), "O título do produto deve ser 'Perfume Oil'.");

            System.out.println("Resposta da adição do produto:");
            System.out.println(jsonResponse.toString(2)); // Imprime a resposta formatada

        } catch (UnirestException | JSONException e){

        }
    }
}
