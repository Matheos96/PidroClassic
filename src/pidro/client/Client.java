package pidro.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9096;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }

    private void start() throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);

        ServerConnection serverConnection = new ServerConnection(socket);

        BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in));

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Thread receiverThread = new Thread(serverConnection);
        receiverThread.start();

        String command;
        while(true) {
            System.out.print("> ");
            command = fromKeyboard.readLine();

            out.println(command);

        }
    }
}
