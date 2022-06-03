import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Serializable {
    public String id;
    private int password;
    private double balance;

    public User(String _id) {
        id = _id;
        password = 1234;
        balance = 10000;
    }

    public String getName() {
        return id;
    }

    public boolean chechBalance(double amount) {
        if (this.balance >= amount) {
            return true;
        }
        return false;
    }

    public void modifyBalances(User receiver, double amount, ArrayList<User> usersList) {
        this.balance -= amount;
        receiver.balance += amount;
        ObjectOutputStream objectOut;
        try {
            objectOut = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream("Users.txt")));
            objectOut.writeObject(usersList);
            objectOut.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static User addUser(ArrayList<User> usersList) {
        String id = Validator1.getString("\nEnter an ID: ");
        User a = new User(id);
        usersList.add(a);
        System.out.println("\nSuccessfully signed up!");
        System.out.println("\nDeafult Password: 1234    Balance = 10000 BYTC");
        System.out.println("------------------------------------------------");

        ObjectOutputStream objectOut;
        try {
            objectOut = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream("Users.txt")));
            objectOut.writeObject(usersList);
            objectOut.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return a;
    }

    public void checkPassword() {
        int password = Validator1.getInt("\nEnter your current password:");
        if (this.password != password) {
            System.out.println("\nInvalid password! Please re-enter your password.");
            this.checkPassword();
        }
    }

    public void changePassword(ArrayList<User> usersList) {
        this.checkPassword();
        int newPassword = Validator1.getInt("\nEnter a 4-digit number as your new password: ");
        int reenterPassword = Validator1.getInt("\nRetype the new password: ");
        if (newPassword == reenterPassword) {
            if (newPassword >= 1000 && newPassword <= 9999) {
                this.password = newPassword;
                ObjectOutputStream objectOut;
                try {
                    objectOut = new ObjectOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream("Users.txt")));
                    objectOut.writeObject(usersList);
                    objectOut.flush();
                    objectOut.close();

                    System.out.println("\nSuccessfully updated the password!");
                    System.out.println("------------------------------------------------");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                System.out.println("\nPlease enter a 4-digit password!");
            }
        } else {
            System.out.println("\nPasswords don't match!");
            changePassword(usersList);
        }
    }

    public void printHistory() {
        File file = new File("Transactions.txt");
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String str = myReader.nextLine();
                if (str.contains(this.id)) {
                    System.out.println(str);
                }
            }
            System.out.println("--------------------------------------------------");
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printBalance() {
        System.out.println("\nYour current balance is : " + this.balance + " BYTC");
        System.out.println("--------------------------------------------------");
    }
}