import java.util.Scanner;

public class ArmstrongChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = scanner.nextInt();
        int originalNum = num, result = 0, digits = 0;

        int temp = num;
        while (temp != 0) {
            digits++;
            temp /= 10;
        }

        temp = num;
        while (temp != 0) {
            int digit = temp % 10;
            result += Math.pow(digit, digits);
            temp /= 10;
        }

        if (result == originalNum)
            System.out.println(originalNum + " is an Armstrong number.");
        else
            System.out.println(originalNum + " is not an Armstrong number.");

        scanner.close();
    }
}
