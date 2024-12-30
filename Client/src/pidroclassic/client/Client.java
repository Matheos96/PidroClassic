package pidroclassic.client;

import java.io.*;
import java.net.*;

public class Client {

    private String serverIp;
    private int serverPort;
    private InetAddress hostAddress;
    private boolean listening = false;
    private Thread listenThread;
    private DatagramSocket socket; //UDP socket
    private final int MAX_PACKET_SIZE = 4096;
    private final byte[] receiveBuffer = new byte[MAX_PACKET_SIZE];



    Client (String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    Client (String serverAddress) {
        String[] parts = serverAddress.split(":");
        serverIp = parts[0];
        try {
            serverPort = Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Server address wrong format!");
        }
    }

    public void sendInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("quit")) {
            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            send(input, "127.0.0.1", 25566);
        }
    }

    public void connect() {
        try {
            hostAddress = InetAddress.getByName(serverIp);
            socket = new DatagramSocket();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        listening = true;
        listenThread = new Thread(this::listen);
        listenThread.start();

    }

    private void listen() {
        DatagramPacket packet;
        while(listening) {
            packet = new DatagramPacket(receiveBuffer, 4096);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            process(packet);

        }
    }

    private void process(DatagramPacket packet) {
        byte[] data = packet.getData();
        String msg = new String(data);
        System.out.println(msg.split("\\|")[0]);

//        ByteArrayInputStream in = new ByteArrayInputStream(packet.getData());
//        ObjectInputStream oIn = new ObjectInputStream(in);
//        Game game = (Game) oIn.readObject();
//        game.printPlayerCards();
    }

    private void send(String msg, String ip, int port) {
        byte[] data = msg.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutdown() {
        socket.close();
    }
}
