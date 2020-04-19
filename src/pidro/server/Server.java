package pidro.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 9096;

    private List<ClientHandler> clients;
    private ExecutorService pool;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

    Server() {
       clients = new ArrayList<>();
       pool = Executors.newFixedThreadPool(4);
    }

    private void start() throws IOException {
        ServerSocket listener = new ServerSocket(PORT);

        Socket client;
        ClientHandler clientThread;
        while (true) {
            System.out.println("[SERVER] Waiting for client connections...");
            client = listener.accept();
            System.out.println("[SERVER] Client connected!");
            clientThread = new ClientHandler(client);
            clients.add(clientThread);

            pool.execute(clientThread);
        }
    }

}
