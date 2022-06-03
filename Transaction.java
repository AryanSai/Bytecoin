import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {
    public String senderId, receiverId;
    public User sender;
    public double amount;
    public String time;
    public static ArrayList<User> usersList = new ArrayList<User>();

    public Transaction(User sender, User receiver, double amount, ArrayList<User> usersList) {
        this.sender = sender;
        senderId = sender.id;
        receiverId = receiver.id;
        this.amount = amount;
        this.time = Validator1.getDate();
        sender.modifyBalances(receiver, amount, usersList);
    }

    public String toString() {
        return senderId + " -> " + receiverId + " : " + amount + " @ " + time + "\n";
    }
}