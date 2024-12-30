package pidroclassic.server;

import pidroclassic.Game;
import pidroclassic.TeamName;
import pidroclassic.exception.GameIsFullException;
import pidroclassic.exception.GameNotReadyException;
import pidroclassic.exception.TeamIsFullException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    private int port;
    private Thread listenThread;
    private volatile boolean listening = false;
    private DatagramSocket socket; //UDP socket

    private final int MAX_PACKET_SIZE = 4096;
    private final byte[] receiveBuffer = new byte[MAX_PACKET_SIZE];

    private Game pidroGame;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        pidroGame = new Game();
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        listening = true;
        listenThread = new Thread(this::listen);
        listenThread.start();
    }

    private void listen() {
        DatagramPacket packet;
        while (listening) {
            packet = new DatagramPacket(receiveBuffer, MAX_PACKET_SIZE);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            process(packet);
        }
    }

    //UDP
    private void process(DatagramPacket packet) {
        //PIDRO|LENGTH|ACTION|DATA|
        String received = new String(packet.getData());
        String[] dataArr = received.split("\\|");

        if (dataArr[0].equals("PIDRO")) {
            if (dataArr.length > 2) {
                System.out.printf("Received data: %s\n", dataArr[2]);
                switch (dataArr[1].toLowerCase()) {
                    case "join":
                        System.out.printf("%s joined!", dataArr[2]);
                        send(String.format("[SERVER] Hello %s!|", dataArr[2]).getBytes(), packet.getAddress(), packet.getPort());
                        try {
                            pidroGame.join(dataArr[2]);
                        } catch (GameIsFullException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "status":
                        System.out.println(pidroGame.isReady());
                        break;
                    default:
                        send("[SERVER] Unknown action!|".getBytes(), packet.getAddress(), packet.getPort());
                        break;
                }
            }
            else if (dataArr.length == 2) {

            }

        }


    }

    public void send(byte[] data, InetAddress address, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testSendGame(InetAddress address, int port) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            oOut.writeObject(pidroGame);
            byte[] data = out.toByteArray();
            System.out.println(data.length);
            pidroGame.printPlayerCards();
            send(data, address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
