package com.desafio.chatgpt;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class CitarProdutos {

    public static void fetchProducts() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/products")
                    .asString();

            if (response.getStatus() == 200) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                JSONArray products = jsonResponse.getJSONArray("products");

                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
                    System.out.println("Produto #" + (i + 1) + ":");
                    System.out.println("ID: " + product.getInt("id"));
                    System.out.println("Nome: " + product.getString("title")); // Assumindo que "title" é um dos campos
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

    public static void main(String[] args) {
        fetchProducts(); // Chama o método para buscar e imprimir os produtos
    }
}
