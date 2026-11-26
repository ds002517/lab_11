package model;

import exceptions.InvalidPasswordException;
import exceptions.InvalidAmountException;
import exceptions.InsufficientFundsException;

public class BankAccount {

    private String customerName;
    private String userId;
    private String password;
    private double balance;

    public BankAccount(String customerName, String userId,
                       String password) {

        if (password == null || password.length() < 8
                || !password.matches(".*\\d.*")) {
            throw new InvalidPasswordException(
                    "Password must contain at least 8 characters and one digit");
        }

        this.customerName = customerName;
        this.userId = userId;
        this.password = password;
        this.balance = 0;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void deposit(double amount) {

        if (!Double.isFinite(amount) || amount <= 0
                || !Double.isFinite(balance + amount)) {
            throw new InvalidAmountException(
                    "Invalid deposit amount");
        }

        balance += amount;
    }

    public void withdraw(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds");
        }

        balance -= amount;
    }
}