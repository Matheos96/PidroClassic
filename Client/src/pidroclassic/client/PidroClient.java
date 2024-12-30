package pidroclassic.client;

public class PidroClient {

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 25567);
        client.connect();
        client.sendInput();
        //client.send("HELLO SERVER!!!!", "127.0.0.1", 25566);
        //client.shutdown();
    }
}
