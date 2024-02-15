package com.desafio.chatgpt;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class CriarProduto {

    public static void addProduct() {
        try {
            HttpResponse<String> response = Unirest.post("https://dummyjson.com/products/add")
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

            if (response.getStatus() == 200) {
                // Converte a resposta em JSON para facilitar o manuseio
                JSONObject jsonResponse = new JSONObject(response.getBody());
                System.out.println("Produto adicionado com sucesso: " + jsonResponse.toString(2)); // Imprime a resposta formatada
            } else {
                System.out.println("Falha ao adicionar o produto: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            System.err.println("Erro ao fazer a requisição: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        addProduct(); // Chama o método para adicionar o produto
    }
}

