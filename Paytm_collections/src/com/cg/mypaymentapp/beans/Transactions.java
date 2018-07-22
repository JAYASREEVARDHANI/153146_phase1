package com.cg.mypaymentapp.beans;

import java.math.BigDecimal;

public class Transactions {
	
	String transactionType;
	BigDecimal amount;
	
	public Transactions() {
		super();
	}

	public Transactions(String transactionType, BigDecimal amount) {
		super();
		this.transactionType = transactionType;
		this.amount = amount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transactions [transactionType=" + transactionType + ", amount=" + amount + "]";
	}

}
