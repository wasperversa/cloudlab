import java.util.Scanner;

public class PasswordProtectedSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pass, ePass;
        int numOne, numTwo, sum;

        System.out.print("Create a Password: ");
        pass = scanner.nextLine();

        System.out.print("\nEnter Two Numbers to Add: ");
        numOne = scanner.nextInt();
        numTwo = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left after nextInt()

        System.out.print("\nEnter the Password to See the Result: ");
        ePass = scanner.nextLine();

        if (pass.equals(ePass)) {
            sum = numOne + numTwo;
            System.out.println("\n" + numOne + " + " + numTwo + " = " + sum);
        } else {
            System.out.println("\nSorry! You've entered a Wrong Password!");
        }

        scanner.close();
    }
}
