import java.util.Scanner;
public class pyramid {

    public static void main(String[] args){
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the number of rows for: ");
        int numrows = scanner.nextInt();
        System.out.println("Pyramid Pattern:");
        printPryramid(numrows);
        scanner.close();
    }
    static void printPryramid(int rows){
        for (int i = 1; i <= rows; i++) {
            // Print spaces
            for (int j = 1; j <= rows - i; j++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}