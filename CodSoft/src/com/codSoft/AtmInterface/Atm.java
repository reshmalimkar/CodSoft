package com.codSoft.AtmInterface;

import java.util.Scanner;

class BankAccount {
	private double balance;

	public BankAccount(double initialBalance) {
		if (initialBalance >= 0) {
			this.balance = initialBalance;
		} else {
			throw new IllegalArgumentException("Initial balance cannot be negative");
		}
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		if (amount > 0) {
			balance += amount;
		} else {
			throw new IllegalArgumentException("Deposit amount must be greater than zero");
		}
	}

	public void withdraw(double amount) {
		if (amount > 0 && balance >= amount) {
			balance -= amount;
		} else if (amount <= 0) {
			throw new IllegalArgumentException("Invalid amount for withdrawal. Amount must be greater than zero.");
		} else {
			throw new IllegalArgumentException("Insufficient balance for withdrawal.");
		}
	}
}

public class Atm {
	private BankAccount bankAccount;
	private String accountNumber;
	private String pin;

	public Atm(String accountNumber, String pin, double initialBalance) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.bankAccount = new BankAccount(initialBalance);
	}

	public void displayBalance() {
		System.out.println("Account Balance: $" + bankAccount.getBalance());
	}

	public void deposit(double amount) {
		if (amount > 0) {
			bankAccount.deposit(amount);
			System.out.println("Deposit successful. Deposited: $" + amount);
			displayBalance();
		} else {
			System.out.println("Invalid amount for deposit. Amount must be greater than zero.");
		}
	}

	public void withdraw(double amount) {
		if (amount > 0 && bankAccount.getBalance() >= amount) {
			bankAccount.withdraw(amount);
			System.out.println("Withdrawal successful. Withdrawn: $" + amount);
			displayBalance();
		} else if (amount <= 0) {
			System.out.println("Invalid amount for withdrawal. Amount must be greater than zero.");
		} else {
			System.out.println("Insufficient balance for withdrawal.");
		}
	}

	public static void main(String[] args) {
		Atm atm = new Atm("123456789", "1234", 1000.0);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to the ATM!");
		System.out.print("Enter your account number: ");
		String enteredAccountNumber = scanner.nextLine();

		System.out.print("Enter your PIN: ");
		String enteredPin = scanner.nextLine();

		if (enteredAccountNumber.equals(atm.accountNumber) && enteredPin.equals(atm.pin)) {
			System.out.println("Login successful.");
			while (true) {
				System.out.println("\n1. Display Balance");
				System.out.println("2. Deposit");
				System.out.println("3. Withdraw");
				System.out.println("4. Exit");
				System.out.print("Enter your choice: ");

				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					atm.displayBalance();
					break;
				case 2:
					System.out.print("Enter the deposit amount: $");
					double depositAmount = scanner.nextDouble();
					atm.deposit(depositAmount);
					break;
				case 3:
					System.out.print("Enter the withdrawal amount: $");
					double withdrawalAmount = scanner.nextDouble();
					atm.withdraw(withdrawalAmount);
					break;
				case 4:
					System.out.println("Thank you for using the ATM. Goodbye!");
					System.exit(0);
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
		} else {
			System.out.println("Login failed. Invalid account number or PIN.");
		}
	}
}
