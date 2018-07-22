package com.cg.mypaymentapp.pl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class Client {
	
	WalletService service;
	
	Client() {
		
		service = new WalletServiceImpl();
	}
	
	public void menu() {
		
		System.out.println("1) Create Account");
		System.out.println("2) Show Balance");
		System.out.println("3) Deposit Amount");
		System.out.println("4) Withdraw Amount");
		System.out.println("5) Fund Transfer");
		System.out.println("6) Print Transactions");
		System.out.println("0) Exit Application");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter your choice");
		int choice = sc.nextInt();
		
		switch(choice) {
		
			case 1:
				
				Customer customer = new Customer();
				Wallet wallet = new Wallet();
				
				System.out.print("Enter name: ");
				String name = sc.next();
				
				System.out.print("Enter mobileNumber: ");
				String mobileNumber = sc.next();
				
				System.out.print("Enter Amount: ");
				BigDecimal amount = sc.nextBigDecimal();
				
				try {
					customer = service.createAccount(name, mobileNumber, amount);
					System.out.println("Your account has been successfully registered");
				} 
				catch (InvalidInputException e) {
					e.printStackTrace();
				}
				break;
				
			case 2:
				
				System.out.println("Enter mobile number");
				mobileNumber = sc.next();
				
			try {
				customer = service.showBalance(mobileNumber);
				System.out.print(customer.getName()+", balance in your account" );
				System.out.println(" is:" + customer.getWallet().getBalance());
			} catch (InvalidInputException e3) {
				e3.printStackTrace();
			}	
				break;
			
			case 3:
				
				System.out.println("Enter mobile number");
				mobileNumber = sc.next();
				
				System.out.println("Enter amount to deposit");
				amount = sc.nextBigDecimal();
				
				try {
					customer = service.depositAmount(mobileNumber, amount);
					System.out.println("Amount deposited Successfully ");
					System.out.println("Account balance is: " + customer.getWallet().getBalance());
				} catch (InvalidInputException e2) {
					e2.printStackTrace();
				}
				break;
			
			case 4:
				
				System.out.println("Enter mobile number");
				mobileNumber = sc.next();
				
				System.out.println("\nEnter amount to  withdraw");
				amount = sc.nextBigDecimal();
				
				try {
					customer = service.withdrawAmount(mobileNumber, amount);
					System.out.println("Amount withdrawn Successfully ");
					System.out.println("Account balance is: " + customer.getWallet().getBalance());
				} catch (InvalidInputException e1) {
					e1.printStackTrace();
				} catch (InsufficientBalanceException e) {
					e.printStackTrace();
				}
				break;
			
			case 5:
				
				System.out.print("\nEnter source mobile number: ");
				String sourceMobile = sc.next();
				
				System.out.print("\nEnter target mobile number: ");
				String targetMobile = sc.next();
				
				System.out.println("\nEnter amount to be transferred");
				amount = sc.nextBigDecimal();
				
				try {
					customer = service.fundTransfer(sourceMobile, targetMobile, amount);
					System.out.println("Amount has been successfully transferred from your account " + customer.getName());
					System.out.println("\nBalance is " + customer.getWallet().getBalance());

				} 
				catch (InvalidInputException e) {
					e.printStackTrace();
				} catch (InsufficientBalanceException e) {
					e.printStackTrace();
				}
				break;
				
			case 6:
				
				System.out.println("\nEnter mobile number");
				String mobileNo = sc.next();
				
				List<Transactions> list = new ArrayList<>();
				list = service.getTransactions(mobileNo);
				
				Iterator<Transactions> it = list.iterator();
				
				while(it.hasNext()) {
					Transactions transaction = it.next();
					System.out.println("Mobile No\t: " + mobileNo);
					System.out.println("Transaction Type: " + transaction.getTransactionType());
					System.out.println("Amount\t: " + transaction.getAmount());
				}
				break;

			case 0:
				System.out.println("Thank you for using our services");
				System.out.println("Good Bye");
				System.exit(0);
			
			default:
				System.out.println("Please enter valid choice");
				break;
		}
				
	}
	
	public static void main(String[] args) {
		
		Client client = new Client();
		
		while(true) {
			client.menu();
		}
	}
	
	
}
