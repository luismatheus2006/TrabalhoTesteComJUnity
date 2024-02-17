package com.desafio.chatgpt.Teste;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class APILoginTeste {

    //Transforma o token e o ID em variaveis globais
    public static String authToken = null;



        @Test
        public void TestaFuncionalidadeDeLoginNaAPI() {
            try {
                //Faz comunicação com a API
                HttpResponse<JsonNode> response = Unirest.post("https://dummyjson.com/auth/login")
                        //Dados necessarios para o Login
                        .header("Content-Type", "application/json")
                        .body("{\n    \"username\": \"kminchelle\",\n    \"password\": \"0lelplR\"\n}")
                        .asJson();
                //Verifica se o Status da requisição foi 200 logo Sucesso
                assertEquals(200, response.getStatus(), "A resposta deve ter status 200 para um login bem-sucedido.");
                //Pega as informações da resposta da API

                JSONObject jsonResponse = new JSONObject(String.valueOf(response.getBody()));
                //Certifica que o Token não é nulo e o id existe
                assertNotNull(jsonResponse.getString("token"), "Um token de autenticação deve ser recebido após o login bem-sucedido.");
                assertTrue(jsonResponse.has("id"), "Um ID de usuário deve ser recebido após o login bem-sucedido.");
                //Armazena o valor do Token e o Id
                authToken = jsonResponse.getString("token");
                int userId = jsonResponse.getInt("id");
                //Printa as informações no terminal
                System.out.println("Token de Autenticação: " + authToken);
                System.out.println("ID do Usuário: " + userId);
                System.out.println("Teste: Login bem-sucedido");
                System.out.println("Status: " + response.getStatus());
                //Caso o teste de errado
            } catch (UnirestException | JSONException e) {
                fail("Erro durante o teste de login: " + e.getMessage());
            }
        }
    }
