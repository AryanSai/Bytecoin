import java.net.*;
import java.io.*;

public class MultiServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        boolean listening = true;
        System.out.println("\n---------------------------");
        System.out.println("\nThe Bytecoin Server is up!!");

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            System.exit(-1);
        }

        try {
            while (listening)
                new Blockchain(serverSocket.accept()).start();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
