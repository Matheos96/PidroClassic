package pidro.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class ServerConnection implements Runnable {

    private Socket server;
    private BufferedReader in;

    ServerConnection(Socket serverSocket) throws IOException {
        this.server = serverSocket;
        this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    @Override
    public void run() {
        int returnCode = 0;
        try {
            while(true) {
                String message = in.readLine();
                if(message.equals("disconnect"))
                    break;

                System.out.printf("Server says: %s\n", message);
            }
        }
        catch (IOException e) {
            System.err.println("IOException in ServerConnection on Client. Closing socket...");
            //System.err.println(Arrays.toString(e.getStackTrace()));
        }
        finally {
            try {
                server.close();
                //Kill the client
            } catch (IOException e) {
                System.err.println("Failed to close server socket in ServerConnection");
                e.printStackTrace();
                returnCode = -1;
            }


        }
        System.exit(returnCode);
    }
}
