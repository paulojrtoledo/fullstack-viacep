import br.com.alura.exceptions.CepNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CepHandler implements HttpHandler {
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            System.out.println("Path recebido: " + path);

            String[] parts = path.split("/");

            if (parts.length < 4) {
                sendJsonError(exchange, "Formato inválido. Use: /api/cep/{cep}", 400);
                return;
            }

            String cep = parts[3];
            System.out.println("CEP extraído: " + cep);

            if (cep == null || cep.trim().isEmpty()) {
                sendJsonError(exchange, "CEP não pode ser vazio", 400);
                return;
            }

            CepResult resultado = viaCepSearch(cep);
            
            String jsonAnswer = gson.toJson(resultado);
            enviarRespostaJson(exchange, jsonAnswer, 200);

        } catch (CepNotFoundException e) {
            sendJsonError(exchange, "CEP não encontrado", 404);
        } catch (Exception e) {
            System.err.println("Erro interno: " + e.getMessage());
            e.printStackTrace();
            sendJsonError(exchange, "Erro interno do servidor", 500);
        }
    }

    private CepResult viaCepSearch(String cep) throws IOException, InterruptedException, CepNotFoundException {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        JsonObject jsonObj = gson.fromJson(json, JsonObject.class);

        if (jsonObj.has("erro")) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        }

        return gson.fromJson(json, CepResult.class);
    }

    private void enviarRespostaJson(HttpExchange exchange, String json, int status) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendJsonError(HttpExchange exchange, String mensagem, int status) throws IOException {
        JsonObject jsonError = new JsonObject();
        jsonError.addProperty("erro", mensagem);
        enviarRespostaJson(exchange, gson.toJson(jsonError), status);
    }
}