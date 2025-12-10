
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class TestMain { // Nome da classe
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/cep", new CepHandler());
        server.start();
        System.out.println("Servidor rodando na porta 8080");
    }
}