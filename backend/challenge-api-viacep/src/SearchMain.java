import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SearchMain {

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/api/cep", new CepHandler());
            server.start();

            System.out.println("Servidor rodando na porta 8080");
            System.out.println("Endpoint: http://localhost:8080/api/cep/{cep}");
            System.out.println("Exemplo: curl http://localhost:8080/api/cep/01310100");

        } catch (IOException e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("Address already in use")) {
                System.err.println("A porta 8080 já está em uso. Feche outros servidores ou use outra porta.");
            }
        }
    }
}