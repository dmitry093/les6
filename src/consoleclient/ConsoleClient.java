package consoleclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8484;

    public ConsoleClient() {
        Socket socket = null;
        System.out.println("Start client...");
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            Scanner sc = new Scanner(socket.getInputStream());
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            new Thread(() -> {
                while (true) {
                    if (!sc.hasNext()) {
                        break;
                    }
                    String str = sc.nextLine();
                    System.out.println("Server: " + str);
                }
            }).start();
            System.out.println("Now you can send messages to server, please write them.");
            Scanner inConsole = new Scanner(System.in);
            while (true) {
                String str = inConsole.nextLine();
                if (str.equals("/exit")) {
                    break;
                }
                pw.println(str);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
