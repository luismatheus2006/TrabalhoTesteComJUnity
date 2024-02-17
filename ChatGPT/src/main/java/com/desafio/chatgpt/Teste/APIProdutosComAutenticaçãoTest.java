package com.desafio.chatgpt.Teste;

import com.desafio.chatgpt.Login;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class APIProdutosComAutenticaçãoTest {
    //Teste para verficar se é possivel chamar os produtos autenticação
    @Test
    public void ProdutosComAutenticação() {
        try {

            // Garante que o token seja obtido antes de fazer a requisição de produtos
            Login.main(null);

            //Loga na API
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    //Dados que vão se usados na chamada
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + Login.AUTH_TOKEN) // Usa o token da classe Login
                    .asString();
            //Se a requisição for bem sucedida (Status 200)
            if (response.getStatus() == 200) {
                //Pega os dados dos produtos e coloca em um array
                JSONArray products = new JSONObject(response.getBody()).getJSONArray("products");
                //Certifica que a lista de produtos não está vazia nem é nula
                assertNotNull(products, "A lista de produtos não deve ser nula.");
                assertTrue(products.length() > 0, "A lista de produtos não deve estar vazia.");

                // Cita todos os produtos
                System.out.println("Produtos com autorização:");
                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
                    System.out.println("Produto #" + (i + 1) + ":");
                    System.out.println("ID: " + product.getInt("id"));
                    System.out.println("Nome: " + product.getString("title"));
                    System.out.println("Descrição: " + product.getString("description"));
                    System.out.println("Estoque: " + product.getInt("stock"));
                    System.out.println("Preço: $" + product.getDouble("price"));
                    System.out.println(); // Linha em branco para separar produtos
                }
                //se o Status não for 200 a requisição falhou e vai printar o status no terminal
            } else {
                fail("Falha \n Status: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ProdutosSemAutorização() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .header("Content-Type", "application/json")
                    .asString();
            System.out.println("message: \"Authentication Problem\" \nStatus " + response.getStatus());
            assertEquals(403, response.getStatus(), "Esperava-se um status 401 (Unauthorized), mas foi recebido: " + response.getStatus());
        } catch (UnirestException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        }
    }

    @Test
    public void ProdutosComAutorizaçãoNula() {
        try {
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer null")
                    .asString();
            System.out.println("\"name\": \"JsonWebTokenError\",");
            System.out.println("\"message\":\"Invalid/Expired Token!\" \nStatus: " + response.getStatus());
            assertEquals(401, response.getStatus(), "Esperava-se um status 401 (Unauthorized), mas foi recebido: " + response.getStatus());
        } catch (UnirestException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        }
    }
}
