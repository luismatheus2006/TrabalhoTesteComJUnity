package com.desafio.chatgpt;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Users {

    public static void main(String[] args) {
        Unirest.setTimeouts(0, 0); // Configura os timeouts para infinito
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/users")
                    .asString();

            if (response.getStatus() == 200) {
                JSONObject jsonResponse = new JSONObject(response.getBody());

                // Supondo que a resposta contenha um array chamado "users"
                JSONArray users = jsonResponse.getJSONArray("users");

                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);

                    // Imprime todas as informações do usuário
                    System.out.println("User #" + (i + 1) + ":");
                    user.keySet().forEach(key -> {
                        System.out.println(key + ": " + user.get(key));
                    });
                    System.out.println(); // Linha em branco para separar usuários
                }
            } else {
                System.out.println("Falha ao obter a resposta: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            System.err.println("Erro ao fazer a requisição: " + e.getMessage());
        }
    }
}