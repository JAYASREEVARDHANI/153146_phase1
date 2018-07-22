package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	private WalletRepo repo;
	List<Transactions> list;
	Map<String, List<Transactions>> transactions;

	public WalletServiceImpl() {
		
		list = new ArrayList<>();
		repo = new WalletRepoImpl();
		transactions = new HashMap<>();
	}

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		
		if(isValid(mobileNo) && isValidName(name) && amount.compareTo(new BigDecimal(0)) > 0) {
			
			Wallet wallet = new Wallet();
			Customer customer = new Customer();

			wallet.setBalance(amount);
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);

			if(repo.save(customer) == true) {
				transactions.put(mobileNo, null);
				return customer;
			}
			else
				throw new InvalidInputException("User already present");
		}
		else throw new InvalidInputException("Enter valid details");
	}

	private boolean isValidName(String name) {
		
		if( name == null || name.trim().isEmpty() )
			return false;
		return true;
	}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		
		if(isValid(mobileNo)) {
			
			Customer customer=repo.findOne(mobileNo);
			if(customer!=null)
				return customer;
			else
				throw new InvalidInputException("Invalid mobile number");
		}
		else 
			throw new InvalidInputException("Enter valid mobile number");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		
		if(isValid(sourceMobileNo) == false || isValid(targetMobileNo) == false || sourceMobileNo.equals(targetMobileNo)) throw new InvalidInputException();
		
		Customer customer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		
		return customer;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException();

		if(isValid(mobileNo)) {
			
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));

			Transactions transaction = new Transactions();
			transaction.setAmount(amount);
			transaction.setTransactionType("Deposit");

			list.add(transaction);
			if(transactions.containsKey(mobileNo)) {
				transactions.remove(mobileNo);
				transactions.put(mobileNo, list);
			}

			repo.remove(mobileNo);

			if(repo.save(customer)) {
				return customer;
			}
		}
		return null;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		if(isValid(mobileNo)) {
			
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().subtract(amount));

			if(amount.compareTo(wallet.getBalance()) > 0) 
				throw new InsufficientBalanceException("Insufficient amount in your account");

			Transactions transaction = new Transactions();
			transaction.setAmount(amount);
			transaction.setTransactionType("Withdraw");

			list.add(transaction);
			if(transactions.containsKey(mobileNo)) {
				transactions.remove(mobileNo);
				transactions.put(mobileNo, list);
			}

			repo.remove(mobileNo);

			repo.save(customer);

			return customer;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}


	public List<Transactions> getTransactions(String mobileNo) {
		
		return transactions.get(mobileNo);
	}

	public boolean isValid(String mobileNo) {
		
		if(mobileNo != null && mobileNo.matches("[1-9][0-9]{9}")) {
			return true;
		} else 
			return false;
	}
}
