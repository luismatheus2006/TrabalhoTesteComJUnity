package com.desafio.chatgpt;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class CitarProdutosComAutenticação {

    public static void main(String[] args) {
        Unirest.setTimeouts(0, 0);
        try {
            // Certifique-se de que Login.main() foi chamado antes para inicializar o AUTH_TOKEN
            Login.main(args); // Garante que o token seja obtido antes de fazer a requisição de produtos

            HttpResponse<String> response = Unirest.get("https://dummyjson.com/products")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Login.AUTH_TOKEN) // Usa o token da classe Login
                    .asString();

            if (response.getStatus() == 200) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                JSONArray products = jsonResponse.getJSONArray("products");

                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
                    // Imprime as informações do produto
                    System.out.println("Produto #" + (i + 1) + ":");
                    System.out.println("ID: " + product.getInt("id"));
                    System.out.println("Nome: " + product.getString("title")); // Certifique-se de que os nomes dos campos estejam corretos
                    System.out.println("Descrição: " + product.getString("description"));
                    System.out.println("Estoque: " + product.getInt("stock"));
                    System.out.println("Preço: $" + product.getDouble("price"));
                    System.out.println(); // Linha em branco para separar produtos
                }
            } else {
                System.out.println("Falha ao obter a resposta: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            System.err.println("Erro ao fazer a requisição: " + e.getMessage());
        }
    }
}