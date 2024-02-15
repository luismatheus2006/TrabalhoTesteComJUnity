package com.desafio.chatgpt;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class BuscarProdutoPorID {

    public static void fetchProductDetails() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/products/1")
                    .asString();

            if (response.getStatus() == 200) {
                JSONObject product = new JSONObject(response.getBody());
                // Imprime os detalhes do produto específico
                System.out.println("Detalhes do Produto:");
                System.out.println("ID: " + product.getInt("id"));
                System.out.println("Nome: " + product.getString("title")); // Assumindo que "title" é um dos campos
                System.out.println("Descrição: " + product.getString("description"));
                System.out.println("Preço: $" + product.getDouble("price"));
                // Adicione mais campos conforme necessário
            } else {
                System.out.println("Falha ao obter a resposta: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            System.err.println("Erro ao fazer a requisição: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        fetchProductDetails(); // Chama o método para buscar e imprimir os detalhes do produto específico
    }
}