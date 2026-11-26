package model;

import exceptions.InvalidAmountException;
import exceptions.WithdrawalLimitException;

import java.time.LocalDate;

public class CheckingAccount extends BankAccount {

    private double dailyWithdrawalLimit = 300;
    private double withdrawnToday = 0;
    private LocalDate withdrawalDate = LocalDate.now();

    public CheckingAccount(String customerName, String userId,
                           String password) {
        super(customerName, userId, password);
    }

    public double getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) {

        if (!Double.isFinite(dailyWithdrawalLimit)
                || dailyWithdrawalLimit < 0) {
            throw new InvalidAmountException(
                    "Invalid daily withdrawal limit");
        }

        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
    }

    @Override
    public void withdraw(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be positive");
        }

        // Оновлення денного ліміту при зміні дати
        if (!withdrawalDate.equals(LocalDate.now())) {
            withdrawnToday = 0;
            withdrawalDate = LocalDate.now();
        }

        // Перевірка денного ліміту
        if (withdrawnToday + amount > dailyWithdrawalLimit) {
            throw new WithdrawalLimitException(
                    "Daily withdrawal limit exceeded");
        }

        super.withdraw(amount);
        withdrawnToday += amount;
    }
}