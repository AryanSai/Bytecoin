import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Wallet {
    public static ArrayList<User> usersList = new ArrayList<User>();
    public static String ip;

    public static void main(String args[]) {
        try {
            ip = args[0];
            // User.addUser(usersList);
            // ObjectOutputStream objectOut1 = new ObjectOutputStream(
            // new BufferedOutputStream(
            // new FileOutputStream("Users.txt")));
            // objectOut1.writeObject(usersList);
            // objectOut1.close();

            // object input stream for user authentication
            ObjectInputStream usersInput = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("Users.txt")));
            usersList = (ArrayList<User>) usersInput.readObject();

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("|~~Welcome to the world of Bytecoin~~|");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            System.out.println("\n 1. Log In" + "\n 2. Create a new account" + "\n 3. Exit ");

            int op = Validator1.getInt("\nEnter your option: ");
            System.out.println("\n--------------------------------------------");

            switch (op) {
                case 1:
                    String id = Validator1.getString("\nEnter your ID: ");
                    User user = checkSender(id, usersList);
                    user.checkPassword();
                    options(user);
                    break;
                case 2:
                    User newUser = User.addUser(usersList);
                    options(newUser);
                    break;
                case 3:
                    System.out.println("\n---Thank you for using Bytecoin---");
                    System.out.println("\n------------------------------------");

                    break;
                default:
                    System.out.println("\nError! Invalid Option.");
                    System.out.println("\n--------------------------------------------");

            }

            usersInput.close();
        } catch (NullPointerException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void options(User user) {
        boolean check = true;
        do {
            System.out.println("\n--------------------------------------------");
            System.out.println("\nHello " + user.id + " !");
            System.out.println("\nWhat do you want to do?");
            System.out.println("\n 1. Transact" +
                    "\n 2. Balance Enquiry" +
                    "\n 3. Print Transaction History" +
                    "\n 4. Change Password" +
                    "\n 5. Log Out");

            int op = Validator1.getInt("\nEnter your option: ");

            switch (op) {
                case 1:
                    transact(user);
                    break;
                case 2:
                    user.printBalance();
                    break;
                case 3:
                    user.printHistory();
                    break;
                case 4:
                    user.changePassword(usersList);
                    break;
                case 5:
                    System.out.println("\nThank you!");
                    System.out.println("\n--------------------------------------------------");
                    break;
                default:
                    System.out.println("\nPlease enter a valid option!");
                    break;
            }
            if (!Validator1.getString("\nDo you want to continue on the app?(y/n)").equalsIgnoreCase("y"))
                check = false;
        } while (check);
    }

    public static void transact(User sender) {
        try {

            Socket socket = new Socket(ip, 1234);

            // object output stream for socket
            ObjectOutputStream objectOutputServer = new ObjectOutputStream(socket.getOutputStream());

            double amount;
            boolean flag = true;
            while (flag) {
                System.out.println("------------------------------------");
                System.out.println("|---Enter the transaction details---|");
                System.out.println("------------------------------------");

                String receiverId = getReceiverId(sender);

                User reciever = checkReciever(receiverId, usersList);

                if (reciever != null) {
                    amount = getAmount();
                    if (sender.chechBalance(amount)) {
                        Transaction t = new Transaction(sender, reciever, amount, usersList);
                        try {
                            objectOutputServer.writeObject(t);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("\nTransaction Succeded!");
                        System.out.println("\n----------------------------------- ");
                    } else {
                        System.out.println("\nInsufficient Balance!");
                    }
                } else {
                    System.out.println("\nThe reciver does not exist!");
                }
                if (!Validator1.getString("\nDo you want to continue transacting?(y/n)").equalsIgnoreCase("y"))
                    flag = false;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("\nFile is empty!");
            e.printStackTrace();
        }
    }

    private static String getReceiverId(User sender) {
        boolean isValid = false;
        String receiverId = "";
        while (isValid == false) {
            receiverId = Validator1.getString("\nEnter the receiver: ");

            if (receiverId.equalsIgnoreCase(sender.id)) {
                System.out.println("You can't send money to yourself!");
                System.out.println("\n----------------------------------- ");
            } else {
                isValid = true;
            }
        }
        return receiverId;
    }

    public static double getAmount() {
        double amount = 0;
        boolean isValid = false;
        while (isValid == false) {
            amount = Validator1.getDouble("\nEnter the amount: ");
            if (amount <= 0) {
                System.out.println("\nInvalid Input!");
                isValid = false;
            } else {
                isValid = true;
            }
        }
        return amount;
    }

    public static User checkSender(String senderId, ArrayList<User> usersList) {
        boolean flag = false;
        for (User i : usersList) {
            if (i.getName().equals(senderId)) {
                flag = true;
                return i;
            }
        }
        if (flag == false) {
            System.out.println("\nYou don't seem to have an account!");
            String str = Validator1.getString("\nDo you want to sign up? (y/n)");
            if (str.equalsIgnoreCase("y")) {
                User u = User.addUser(usersList);
                return u;
            } else {
                try {
                    main(null);
                } catch (NullPointerException e) {

                }
            }
        }
        return null;
    }

    public static User checkReciever(String id, ArrayList<User> usersList) {
        boolean flag = false;
        for (User i : usersList) {
            if (i.getName().equals(id)) {
                flag = true;
                return i;
            }
        }
        if (flag == false) {
            return null;
        }
        return null;
    }
}