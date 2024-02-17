package com.desafio.chatgpt.Teste;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class APIBuscarProdutoPorIDTeste {

    @Test
    public void TesteProdutoComIdValido() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/products/2").asString();
            //Certifica se a requisição deu certo e printa o detalhe dos produtos
            if (response.getStatus() == 200) {
                JSONObject product = new JSONObject(response.getBody());
                assertNotNull(product, "Os detalhes do produto não devem ser nulos.");
                assertTrue(product.has("id"), "O produto deve ter um ID.");
                assertTrue(product.has("title"), "O produto deve ter um título.");
                assertTrue(product.has("description"), "O produto deve ter uma descrição.");
                assertTrue(product.has("price"), "O produto deve ter um preço.");

                // Citar todas as informações do produto buscado
                System.out.println("Produto buscado:");
                System.out.println("ID: " + product.getInt("id"));
                System.out.println("Título: " + product.getString("title"));
                System.out.println("Descrição: " + product.getString("description"));
                System.out.println("Preço: $" + product.getDouble("price"));

            } else {
                fail("Falha ao obter a resposta: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void ProdutoComIdInvalido() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/products/invalid_id").asString();
            //Ver se deu o codigo de erro certo e printa o resultado
            if (response.getStatus() == 404) {
                System.out.println("ID do produto inválido.");
            } else {
                fail("Esperava-se um status 404 (Not Found), mas foi recebido: " + response.getStatus());
            }
        } catch (UnirestException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        }
    }

    @Test
    public void ProdutoComIdNulo() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/products/null").asString();
            //Ver se deu o codigo de erro certo e printa o resultado
            if (response.getStatus() == 404) {
                System.out.println("ID do produto nulo.\n ");
            } else {
                fail("Esperava-se um status 404 (Not Found), mas foi recebido: " + response.getStatus());
            }
        } catch (UnirestException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        }
    }
}