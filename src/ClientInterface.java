import exceptions.*;
import model.BankAccount;
import model.CheckingAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    private final List<BankAccount> accounts = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() throws IOException {

        String option;

        while (true) {
            printMainMenu();
            option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1":
                        addNewAccount();
                        break;
                    case "2":
                        deleteAccount();
                        break;
                    case "3":
                        deposit();
                        break;
                    case "4":
                        withdraw();
                        break;
                    case "5":
                        printInfo();
                        break;
                    case "6":
                        return;
                    default:
                        throw new AccountException(
                                "Please enter a valid choice (1 thru 6)");
                }
            } catch (AccountException e) {
                System.err.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void printMainMenu() throws IOException {

        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    private void addNewAccount() {

        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        var name = scanner.nextLine();

        System.out.print(">>> Enter user id: ");
        var userId = scanner.nextLine();

        System.out.print(">>> Enter user password: ");
        String password = scanner.nextLine();

        System.out.print(">>> Confirm password: ");
        String confirmPassword = scanner.nextLine();

        // Перевірка унікальності userId
        for (BankAccount account : accounts) {
            if (account.getUserId().equals(userId)) {
                throw new DuplicateUserIdException(
                        "User ID already exists");
            }
        }

        // Перевірка підтвердження паролю
        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(
                    "Passwords do not match");
        }

        BankAccount account = new CheckingAccount(
                name, userId, password);

        accounts.add(account);

        System.out.println("Account created successfully.");
    }

    private void deleteAccount() {

        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.nextLine();

        System.out.print(">>> Enter user password: ");
        var password = scanner.nextLine();

        BankAccount account = getAccount(userId, password);

        // Перевірка балансу перед видаленням
        if (account.getBalance() != 0) {
            throw new NonZeroBalanceException(
                    "Cannot delete an account with a non-zero balance");
        }

        accounts.remove(account);

        System.out.println("Account deleted successfully.");
    }

    private void deposit() {

        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.nextLine();

        System.out.print(">>> Enter user password: ");
        var password = scanner.nextLine();

        BankAccount account = getAccount(userId, password);

        System.out.print(">>> Enter amount: ");
        double amount = readAmount();

        account.deposit(amount);

        System.out.println("Deposit completed successfully.");
        System.out.println("Current balance: "
                + account.getBalance());
    }

    private void withdraw() {

        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.nextLine();

        System.out.print(">>> Enter user password: ");
        var password = scanner.nextLine();

        BankAccount account = getAccount(userId, password);

        System.out.print(">>> Enter amount: ");
        double amount = readAmount();

        account.withdraw(amount);

        System.out.println("Withdrawal completed successfully.");
        System.out.println("Current balance: "
                + account.getBalance());
    }

    private void printInfo() {

        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.nextLine();

        System.out.print(">>> Enter user password: ");
        var password = scanner.nextLine();

        BankAccount account = getAccount(userId, password);

        System.out.println("\nAccount info: ");

        System.out.println(" - customer name: "
                + account.getCustomerName());

        System.out.println(" - account balance: "
                + account.getBalance());

        if (account instanceof CheckingAccount) {

            CheckingAccount checkingAccount =
                    (CheckingAccount) account;

            System.out.println(" - account type: Checking");

            System.out.println(" - account daily withdrawal limit: "
                    + checkingAccount.getDailyWithdrawalLimit());
        }
    }

    // Пошук рахунку та перевірка паролю
    private BankAccount getAccount(String userId,
                                   String password) {

        for (BankAccount account : accounts) {

            if (account.getUserId().equals(userId)) {

                if (!account.checkPassword(password)) {
                    throw new InvalidPasswordException(
                            "Incorrect password");
                }

                return account;
            }
        }

        throw new AccountNotFoundException(
                "Account not found");
    }

    // Перевірка формату введеної суми
    private double readAmount() {

        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidAmountException(
                    "Please enter a valid amount");
        }
    }
}