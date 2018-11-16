package consoleserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {
    private boolean clientConnected = false;

    public ConsoleServer() {
        ServerSocket serverSocket = null;
        Socket sock = null;
        try {
            serverSocket = new ServerSocket(8484);
            System.out.println("Start server...");
            sock = serverSocket.accept();
            System.out.println("Client connected");
            clientConnected = true;
            Scanner sc = new Scanner(sock.getInputStream());
            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            new Thread(() -> {
                System.out.println("Now you can send messages to client, please write them.");
                Scanner inConsole = new Scanner(System.in);
                while (true) {
                    if (!clientConnected) {
                        break;
                    }
                    String str = inConsole.nextLine();
                    pw.println(str);
                    pw.flush();
                }
            }).start();
            while (true) {
                if (!sc.hasNext()) break;
                String str = sc.nextLine();
                System.out.println("Client: " + str);
            }
            clientConnected = false;
            System.out.println("Client disconnected");
        } catch (IOException e) {
            System.out.println("Error initialization server");
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
