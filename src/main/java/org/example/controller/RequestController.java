package org.example.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.methods.Body;
import org.json.JSONObject;

public class RequestController {
    HttpClient client = HttpClient.newHttpClient();
    Dotenv dotenv = Dotenv.load();
    String endpointToken = dotenv.get("ENDPOINT_TOKEN");
    String endpointPagamento = dotenv.get("ENDPOINT_PAGAMENTO");

    public String TokenRequest() throws IOException, InterruptedException {
        /**
         * String clientId = dotenv.get("CLIENT_ID");
         * String username = dotenv.get("USERNAME");
         * String password = dotenv.get("PASSWORD");
         */
        Map<String, String> data = Map.of(
                "clientId", "coloque aqui o id",
                "username", "coloque o email",
                "password", "coloque a senha");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(data);


        //monta a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        //envia a requisição e captura a resposta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //pega o corpo da resposta e converte para JSON, depois captura o valor da chave IdToken e salva em 'token
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONObject authResult = jsonResponse.getJSONObject("AuthenticationResult");
        String token = authResult.getString("IdToken");
        return token;
    }


    public void chamaPagamento(double value, String paymentMethod, String paymentType, String paymentMethodSubType) throws IOException, InterruptedException{
        Body pm = new Body();
        String json = pm.body(value, paymentMethod, paymentType, paymentMethodSubType);

        System.out.println("JSON Enviado:\n" + json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointPagamento))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + TokenRequest())
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("ok");
            System.out.println(response.body());
        } else {
            System.out.println("Erro ao realizar pagamento!");
        }
    }
}
