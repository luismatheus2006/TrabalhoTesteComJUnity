package com.desafio.Sicredi.Teste;

// Importações necessárias para realizar requisições HTTP, manipular JSON e realizar testes.
import com.desafio.Sicredi.Login; // Classe que supostamente contém o token de autenticação.
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

// Importação estática para asserções do JUnit.
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de teste para validar o acesso autenticado a um conjunto de produtos em uma API.
 */
public class TesteBuscarProdutosAutenticação {

    /**
     * Testa o acesso ao endpoint de produtos usando um token de autenticação válido.
     * Verifica se a resposta é 200 OK e se uma lista de produtos é retornada.
     */
    @Test
    public void AcessoComTokenValido() {
        try {
            Login.main(null);
            // Envia uma requisição GET incluindo o token de autenticação no cabeçalho.
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .header("Authorization", "Bearer " + Login.AUTH_TOKEN)
                    .asString();

            // Verifica se o status da resposta é 200, indicando sucesso.
            assertEquals(200, response.getStatus(), "A resposta deve ser 200 OK para acesso com token válido.");

            // Extrai e verifica a lista de produtos da resposta.
            JSONObject responseBody = new JSONObject(response.getBody());
            JSONArray products = responseBody.getJSONArray("products");
            assertNotNull(products, "Deve retornar uma lista de produtos.");
            assertTrue(products.length() > 0, "A lista de produtos não deve estar vazia.");

            System.out.println("Acesso com Token Válido: Sucesso - " + products.length() + " produtos retornados.");
        } catch (UnirestException e) {
            fail("Erro na requisição: " + e.getMessage());
        }
    }
    /**
     * Testa o acesso ao endpoint de produtos sem fornecer um token de autenticação.
     * Espera-se um status 401 Unauthorized ou 403 Forbidden.
     */
    @Test
    public void Tokenautenticaçãoausente() {
        try {
            // Envia uma requisição GET sem token de autenticação.
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .asString();

            // Verifica se o status da resposta indica falha de autenticação.
            assertEquals(403, response.getStatus(), "A resposta deve ser 401 Unauthorized ou 403 Forbidden para requisições sem token de autenticação.");

            System.out.println("Sem Token de Autenticação: Sucesso - Acesso negado conforme esperado.");
        } catch (UnirestException e) {
            fail("Erro na requisição sem token de autenticação: " + e.getMessage());
        }
    }

    /**
     * Testa o acesso ao endpoint de produtos com um token de autenticação inválido.
     */
    @Test
    public void TokenDeAutenticacaoInvalido() {
        try {
            // Envia uma requisição GET com um token inválido.
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .header("Authorization", "Bearer token_invalido")
                    .asString();

            // Verifica se o status da resposta indica token inválido.
            assertTrue(response.getStatus() == 401 , "A resposta deve ser 401 Unauthorized ou 403 Forbidden para token inválido.");

            System.out.println("Token de Autenticação Inválido: Sucesso - Acesso negado conforme esperado.");
        } catch (UnirestException e) {
            fail("Erro na requisição com token de autenticação inválido: " + e.getMessage());
        }
    }
    /**
     * Verifica a estrutura dos dados dos produtos retornados, incluindo a presença dos campos essenciais.
     */
    @Test
    public void estruturaDosDadosDosProdutos() {
        try {
            Login.main(null);
            // Envia uma requisição GET com um token válido.
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .header("Authorization", "Bearer " + Login.AUTH_TOKEN)
                    .asString();

            // Verifica se o status da resposta é 200 OK.
            assertEquals(200, response.getStatus(), "A resposta deve ser 200 OK.");

            // Verifica a presença dos campos essenciais em cada produto.
            JSONObject responseBody = new JSONObject(response.getBody());
            JSONArray products = responseBody.getJSONArray("products");
            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                assertTrue(product.has("id") && product.has("title") && product.has("price"), "Cada produto deve conter os campos 'id', 'title' e 'price'.");
            }

            System.out.println("Estrutura dos Dados dos Produtos: Sucesso - Estrutura de dados validada.");
        } catch (UnirestException e) {
            fail("Erro na validação da estrutura de dados dos produtos: " + e.getMessage());
        }
    }

    /**
     * Valida os campos dos produtos retornados, como verificar se os preços dos produtos são positivos.
     */
    @Test
    public void validacaoDeCamposDosProdutos() {
        try {
            Login.main(null);
            // Envia uma requisição GET com um token válido.
            HttpResponse<String> response = Unirest.get("https://dummyjson.com/auth/products")
                    .header("Authorization", "Bearer " + Login.AUTH_TOKEN)
                    .asString();

            // Verifica se o status da resposta é 200 OK.
            assertEquals(200, response.getStatus(), "A resposta deve ser 200 OK.");

            // Verifica se os preços dos produtos são positivos.
            JSONObject responseBody = new JSONObject(response.getBody());
            JSONArray products = responseBody.getJSONArray("products");
            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                assertNotNull(product.getDouble("id") , "O preço do produto deve ser positivo.");
                assertNotNull(product.getString("title"), "O preço do produto deve ser positivo.");
                assertNotNull(product.getDouble("price") > 0, "O preço do produto deve ser positivo.");
            }

            System.out.println("Validação de Campos dos Produtos: Sucesso - Todos os produtos têm preços positivos.");
        } catch (UnirestException e) {
            fail("Erro na validação dos campos dos produtos: " + e.getMessage());
        }
    }
}
