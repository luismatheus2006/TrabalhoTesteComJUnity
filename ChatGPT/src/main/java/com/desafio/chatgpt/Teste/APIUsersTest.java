package com.desafio.chatgpt.Teste;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class APIUsersTest {

    @Test
    public void ListaDeUsuariosNaoEstaVazia() throws UnirestException, JSONException {
        //Faz a ligação com a API
        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://dummyjson.com/users").asJson();
        //Certifica que o status foi 200
        assertEquals(200, jsonResponse.getStatus(), "A resposta deve ter status 200.");
        //Guarda as informações dos usuarios em um Array
        JSONArray users = jsonResponse.getBody().getObject().getJSONArray("users");
        //Certifica que a lista de usuaruios não está vazia
        assertTrue(users.length() > 0, "A lista de usuários não deve estar vazia.");
        //Imprime no terminal o Status
        System.out.println("Status: "+ jsonResponse.getStatus());
    }

    @Test
    public void VerificaNumeroDeUsuarios() throws UnirestException, JSONException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://dummyjson.com/users").asJson();
        JSONArray users = jsonResponse.getBody().getObject().getJSONArray("users");
        //Contabiliza quantos usuarios tem na lista
        int expectedNumberOfUsers = users.length(); // O valor esperado deve corresponder ao total de usuários fornecidos pela sua API
        //Compara o numero de usuarios com eles mesmos
        assertEquals(expectedNumberOfUsers, users.length(), "O número de usuários deve corresponder ao esperado.");
        System.out.println("Status:"+ jsonResponse.getStatus()+ "\n- Total de usuários: " + users.length());
    }

    @Test
    public void VerificaSeUmUsuarioEspecificoExiste() throws UnirestException, JSONException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://dummyjson.com/users").asJson();
        JSONArray users = jsonResponse.getBody().getObject().getJSONArray("users");
        //Cria uma variavel "userfound" e a declara como falsa
        boolean userFound = false;
        //Aqui deve ser colocado o primeiro e ultimo nome de um usuario real na lista para verifcar que ele esta sendo listado
        String userNameToFind = "Edwina Ernser";
        //Vai repetir a ação = ao numero de usuarios
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            //pega o primeiro nome
            String firstName = user.optString("firstName"); // Ajuste isso com base na estrutura do seu JSON
            //pega o ultimo nome
            String lastName = user.optString("lastName"); // Ajuste isso com base na estrutura do seu JSON
            //junta os dois
            String fullName = firstName + " " + lastName;
            //Se o first e o last de algum usuario da lista for igual ao que queremos o usurFound vai ser verdadeiro
            if (fullName.equals(userNameToFind)) {
                userFound = true;
                break;
            }
        }

        assertTrue(userFound, "O usuário '" + userNameToFind + "' deve estar presente na lista.");
        System.out.println("Status: "+jsonResponse.getStatus() +"\n"+ userNameToFind + " foi encontrado.");
    }

    @Test
    public void VerificaSeRetornaSucessoParaUmUsuarioQueNaoExiste() throws UnirestException, JSONException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get("https://dummyjson.com/users").asJson();
        JSONArray users = jsonResponse.getBody().getObject().getJSONArray("users");
        boolean userFound = false;
        //Armazena um nome de usuario que não existe
        String nonExistingUserName = "Non Existing User"; // Nome que você acredita não estar na lista

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            String firstName = user.optString("firstName"); // Ajuste isso com base na estrutura do seu JSON
            String lastName = user.optString("lastName"); // Ajuste isso com base na estrutura do seu JSON
            String fullName = firstName + " " + lastName;
            //Se não achou o usuario não existente a busca foi um sucesso
            if (fullName.equals(nonExistingUserName)) {
                userFound = true;
                break;
            }
        }
        assertFalse(userFound, "Usuários inexistentes não devem ser encontrados na lista.");
        System.out.println("Status: 200 \n" + nonExistingUserName + " não foi encontrado.");
    }
}