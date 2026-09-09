import java.util.InputMismatchException;
import java.util.Scanner;

public class atm {

    private static double balance = 5000.00;

    // Check account balance
    public static void checkBalance() {
        System.out.printf("Current Balance: Rs. %.2f%n", balance);
    }

    // Deposit money
    public static void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Deposit amount must be greater than zero."
            );
        }

        balance += amount;

        System.out.printf(
            "Rs. %.2f deposited successfully.%n", amount
        );

        checkBalance();
    }

    // Withdraw money
    public static void withdraw(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Withdrawal amount must be greater than zero."
            );
        }

        if (amount > balance) {
            throw new ArithmeticException(
                "Insufficient funds in your account."
            );
        }

        balance -= amount;

        System.out.printf(
            "Rs. %.2f withdrawn successfully.%n", amount
        );

        checkBalance();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        System.out.println("===== ATM MACHINE =====");

        // Keeps the application running until user selects Exit
        while (choice != 4) {

            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {

                // Handle non-integer menu input
                if (!scanner.hasNextInt()) {
                    scanner.next();
                    throw new IllegalArgumentException(
                        "Please enter a valid numeric choice."
                    );
                }

                choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        checkBalance();
                        break;

                    case 2:
                        System.out.print(
                            "Enter amount to deposit: Rs. "
                        );

                        if (!scanner.hasNextDouble()) {
                            scanner.next();
                            throw new IllegalArgumentException(
                                "Please enter a valid amount."
                            );
                        }

                        double depositAmount = scanner.nextDouble();
                        deposit(depositAmount);
                        break;

                    case 3:
                        System.out.print(
                            "Enter amount to withdraw: Rs. "
                        );

                        if (!scanner.hasNextDouble()) {
                            scanner.next();
                            throw new IllegalArgumentException(
                                "Please enter a valid amount."
                            );
                        }

                        double withdrawAmount = scanner.nextDouble();
                        withdraw(withdrawAmount);
                        break;

                    case 4:
                        System.out.println(
                            "Thank you for using the ATM."
                        );
                        break;

                    default:
                        throw new IllegalArgumentException(
                            "Invalid choice! Select between 1 and 4."
                        );
                }

            } catch (ArithmeticException e) {

                // Handles insufficient balance
                System.out.println(
                    "Transaction Error: " + e.getMessage()
                );

            } catch (IllegalArgumentException e) {

                // Handles invalid input
                System.out.println(
                    "Input Error: " + e.getMessage()
                );

            } catch (Exception e) {

                // Handles unexpected exceptions
                System.out.println(
                    "Unexpected Error: " + e.getMessage()
                );

            } finally {

                // Always executed
                System.out.println(
                    "Transaction process completed."
                );
            }
        }

        scanner.close();
    }
}
