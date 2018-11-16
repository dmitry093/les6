package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    private Vector<ClientHandler> clients;

    public Server() {
        clients = new Vector();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8383);
            System.out.println("Start server...");

            while (true) {
                socket = server.accept();
                System.out.println("Client connected");
                clients.add(new ClientHandler(this, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void broadcast(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
