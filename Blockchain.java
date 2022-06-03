import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class Blockchain extends Thread {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    Socket s = null;

    public Blockchain(Socket socket) {
        super("BlockchainThread");
        this.s = socket;
    }

    public synchronized void run() {

        boolean listening = true;
        try {
            // blockchain.add(new Block("0", null)); // genesis block
            // ObjectOutputStream blockOutput1 = new ObjectOutputStream(
            // new BufferedOutputStream(
            // new FileOutputStream("BlockFile.txt", true)));
            // blockOutput1.writeObject(blockchain);
            // blockOutput1.flush();
            // blockOutput1.close();

            ObjectInputStream serverInput = new ObjectInputStream(s.getInputStream());

            ObjectInputStream blockInput = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("BlockFile.txt")));
            blockchain = (ArrayList<Block>) blockInput.readObject();

            BufferedWriter out = new BufferedWriter(
                    new FileWriter("Transactions.txt", true));

            ObjectOutputStream blockOutput = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream("BlockFile.txt", true)));

            while (listening) {
                Transaction t = (Transaction) serverInput.readObject();

                out.append(t.toString());
                out.flush();

                blockchain.add(new Block(blockchain.get(blockchain.size() - 1).hash, t));
                System.out.println("\nNew Block mined!!");
                System.out.println("\n The transaction in the new block is:\n" + t.toString());

                blockOutput.writeObject(blockchain);
                blockOutput.flush();
            }

            out.close();
            blockOutput.close();
            blockInput.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found Exception!");
            System.out.println("-----------------------------");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("The Client disconnected!");
        } catch (NullPointerException e) {
            System.out.println("File is empty");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
