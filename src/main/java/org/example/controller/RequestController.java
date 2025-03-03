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

/**
 * A classe RequestController é responsável por
 * abrigar os métodos que realizarão as requisições.
 */
public class RequestController {
    /**
     * Bloco abaixo carrega as variáveis
     * de ambiente e cria um cliente http.
     */
    Dotenv dotenv = Dotenv.load();
    String endpointToken = dotenv.get("ENDPOINT_TOKEN");
    String endpointPagamento = dotenv.get("ENDPOINT_PAGAMENTO");
    HttpClient client = HttpClient.newHttpClient();

    /**
     * O metodo TokenRequest vai realizar a validação do
     * usuário e retornar o token de autenticação. Esse
     * token será enviado no cabeçalho da req de pagamento.
     */
    public String TokenRequest() throws IOException, InterruptedException {
        // Monta um json com as credenciais necessárias para a autenticação.
        // Tentei fazer salvando as credenciais em um arquivo .env, mas não consegui :(
        Map<String, String> data = Map.of(
                "clientId", "coloque o id",
                "username", "coloque o email",
                "password", "coloque a senha");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(data);

        // Monta a requisição.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        // Envia a requisição
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Pega o body da resposta e converte para JSON. Depois,filtra
        // a chave IdToken do JSON e captura o valor (token).
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONObject authResult = jsonResponse.getJSONObject("AuthenticationResult");
        String token = authResult.getString("IdToken");
        return token;
    }


    /**
     * O metodo chamaPagamento é responsável por realizar a requisição
     * de pagamento. Nela, vai ser chamado o body contendo os dados da
     * transação informados pelo usuário.
     */
    public void chamaPagamento(double value, String paymentMethod, String paymentType, String paymentMethodSubType) throws IOException, InterruptedException{
        Body pm = new Body();
        String json = pm.body(value, paymentMethod, paymentType, paymentMethodSubType);

        // Linha abaixo para ver o body que está sendo enviado.
        // System.out.println("JSON Enviado:\n" + json);

        // Monta a requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointPagamento))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + TokenRequest())
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        // Envia a requisição.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Retorna uma msg e o body da resposta se não for 200.
        if (response.statusCode() != 200)
            System.out.println("Num deu certo :x\n " + response.body());
    }
}
