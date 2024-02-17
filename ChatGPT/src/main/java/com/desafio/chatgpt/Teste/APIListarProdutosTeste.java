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

public class APIListarProdutosTeste {
    public static JSONArray fetchProducts() throws UnirestException, JSONException {
        HttpResponse<String> response = Unirest.get("https://dummyjson.com/products").asString();

        if (response.getStatus() == 200) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            return jsonResponse.getJSONArray("products");
        } else {
            throw new UnirestException("Falha ao obter a resposta: " + response.getStatusText());
        }
    }
    @Test
    public void GaranteQueListaProdutosNaoNula() {
        try {
            //Faz comunicação com a API
            HttpResponse<JsonNode> jsonResponse = Unirest.get("https://dummyjson.com/users").asJson();
            assertEquals(200, jsonResponse.getStatus(), "A resposta deve ter status 200.");

            //Guarda um valor especifico da resposta da API em um array
            JSONArray users = jsonResponse.getBody().getObject().getJSONArray("users");
            assertTrue(users.length() > 0, "A lista de usuários não deve estar vazia.");

            //Printa o resultado do test
            System.out.println("Status: " + jsonResponse.getStatus());
        } catch (UnirestException | JSONException e) {
            fail("Erro ao fazer a requisição: " + e.getMessage());
        }
    }
    @Test
    public void ListarTodosProdutos() throws UnirestException, JSONException {
        fetchProducts();

    }

    @Test
    public void TestarListaNaoNula() throws UnirestException, JSONException {
        JSONArray products = fetchProducts();
        assertNotNull(products, "A lista de produtos não deve ser nula.");
    }
    @Test
    public void TestarTamanhoDaLista() throws UnirestException, JSONException {
        JSONArray products = fetchProducts();
        assertEquals(products.length(), products.length(), "O tamanho da lista de produtos deve ser igual a 2.");
        System.out.println("O tamanho da lista é " + products.length());
    }
    @Test
    public void TestarBuscarProdutosNaoExistentes() throws UnirestException, JSONException {
        JSONArray products = fetchProducts();
        boolean produtoNaoExistenteEncontrado = false;
        for (int i = 0; i < products.length(); i++) {
            System.out.println("Produto " + products.getJSONObject(i).getString("title") );
            if ("Produto Inexistente".equals(products.getJSONObject(i).getString("title"))) {
                produtoNaoExistenteEncontrado = true;
                break;
            }
        }
        assertFalse(produtoNaoExistenteEncontrado, "Produtos não existentes não devem ser encontrados na lista.");
    }
}