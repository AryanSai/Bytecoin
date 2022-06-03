import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is about prompting the user for the specific input values.
 * This class consists of static methods which are utitlity based.
 */
public class Validator1 {
    /**
     * This method returns the current time along with the date
     **/
    public static String getDate() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = date.format(dateFormat);
        return formattedDate;
    }

    /**
     * This method prompts the user to key-in any string as input
     **/
    public static String getString(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt);
        String s = sc.next(); // read user entry
        sc.nextLine(); // discard any other data entered on the line
        return s;
    }

    /**
     * This method prompts the user to key-in any integer as input
     **/
    public static int getInt(String prompt) {
        Scanner sc = new Scanner(System.in);
        int i = 0;
        boolean isValid = false;
        while (isValid == false) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                isValid = true;
            } else {
                System.out.println("Error! Invalid integer value. Try again.");
            }
            sc.nextLine(); // discard any other data entered on the line
        }
        return i;
    }

    public static double getDouble(String prompt) {
        Scanner sc = new Scanner(System.in);
        double d = 0;
        boolean isValid = false;
        while (isValid == false) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                d = sc.nextDouble();
                isValid = true;
            } else {
                System.out.println("Error! Invalid decimal value. Try again.");
            }
            sc.nextLine(); // discard any other data entered on the line
        }
        return d;
    }

    public static char getChar(String prompt) {
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt);
        char c = sc.next().charAt(0);
        return c;
    }

}