package pidro.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    ClientHandler(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while(true) {
                String request = in.readLine();

                if (request == null)
                    break;

                if (request.contains("name"))
                    out.println("NAMNI");
                else if (request.equals("quit"))
                    out.println("disconnect");
                else
                    out.println("Say nating me 'name'");
            }
        }
        catch (IOException e) {
            System.err.println("IOException in run in ClientHandler");
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
