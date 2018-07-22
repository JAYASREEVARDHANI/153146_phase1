package com.cg.mypaymentapp.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;


public class TestClass {

	static WalletService service;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		service = new WalletServiceImpl();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=InvalidInputException.class)
	public void testCreateAccount1() throws InvalidInputException 
	{
		service.createAccount(null, "7093927557", new BigDecimal(2000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount2() throws InvalidInputException 
	{
		service.createAccount("", "7093927557", new BigDecimal(2000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3() throws InvalidInputException 
	{
		service.createAccount("jayasree", "", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4() throws InvalidInputException 
	{
		service.createAccount("jayasree", "", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5() throws InvalidInputException 
	{
		service.createAccount("", "", new BigDecimal(1500));
	}
	
	@Test
	public void testCreateAccount6() throws InvalidInputException 
	{
		Customer actual = service.createAccount("jayasree", "7093927587", new BigDecimal(5000));
		Customer expected = null;
		
		assertNotEquals(expected, actual);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount7() throws InvalidInputException 
	{
		service.createAccount("jayasree", "7093927557", new BigDecimal(9000));
		service.createAccount("priya", "7093927557", new BigDecimal(10000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount8() throws InvalidInputException 
	{
		service.createAccount("jayasree", "7093927557", new BigDecimal(-100));
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount9() throws InvalidInputException 
	{
		Customer actual=service.createAccount("jayasree", null, new BigDecimal(5000.75));
	}
	
	@Test
	public void testCreateAccount10() throws InvalidInputException 
	{
		Customer actual=service.createAccount("Amit", "9849830146", new BigDecimal(5000.75));
		Customer expected=null;
		
		assertNotEquals(expected, actual);
	}


	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance11() throws InvalidInputException 
	{
		service.showBalance(null);		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance12() throws InvalidInputException 
	{
		service.showBalance("");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance13() throws InvalidInputException 
	{
		service.showBalance("12345");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance14() throws InvalidInputException 
	{
		service.showBalance("98498hg");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance15() throws InvalidInputException 
	{
		service.showBalance("98498301467");		
	}
	
	
	@Test
	public void testShowBalance16() throws InvalidInputException 
	{
		service.createAccount("jayasree", "9949990579", new BigDecimal(7000));
		Customer customer=service.showBalance("9949990579");
		BigDecimal expectedResult=new BigDecimal(7000);
		BigDecimal obtainedResult=customer.getWallet().getBalance();
		
		assertEquals(expectedResult, obtainedResult);
		
	}
	
	@Test
	public void testWithdrawAmount() throws InvalidInputException, InsufficientBalanceException {
		String name = "jayasree";
		String mobileNumber = "7095134721";
		BigDecimal balance = new BigDecimal(7000);
		
		service.createAccount(name, mobileNumber, balance);
		
		BigDecimal amount = new BigDecimal(3000);
		
		Customer customer = service.withdrawAmount(mobileNumber, amount);
		assertEquals(new BigDecimal(4000), customer.getWallet().getBalance());
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawAmount1() throws InvalidInputException, InsufficientBalanceException {
		String name = "jayasree";
		String mobileNumber = "7095134611";
		BigDecimal balance = new BigDecimal(2000);
		
		service.createAccount(name, mobileNumber, balance);
		
		BigDecimal amount = new BigDecimal(3000);
		
		service.withdrawAmount(mobileNumber, amount);
	}
	
	@Test
	public void testDepositAmount() throws InvalidInputException {
		String name = "jayasree";
		String mobileNumber = "7095134612";
		BigDecimal balance = new BigDecimal(3000);
		
		Customer customer = service.createAccount(name, mobileNumber, balance);
		
		Customer customer1 = service.depositAmount(mobileNumber, new BigDecimal(3000));
		
		assertEquals(new BigDecimal(6000), customer1.getWallet().getBalance());
	}
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount1() throws InvalidInputException {
		String name = "jayasree";
		String mobileNumber = "7095134619";
		BigDecimal balance = new BigDecimal(2000);
		
		Customer customer = service.createAccount(name, mobileNumber, balance);
		
		Customer customer1 = service.depositAmount(mobileNumber, new BigDecimal(0));
		
	}
	
	@Test(expected = InvalidInputException.class)
	public void testMobileNumber() throws InvalidInputException, InsufficientBalanceException {
		String name = "jayasree";
		String mobileNumber = "70939275573";
		BigDecimal balance = new BigDecimal(3000);
		
		service.createAccount(name, mobileNumber, balance);
		
		BigDecimal amount = new BigDecimal(3000);
		
		service.withdrawAmount(mobileNumber, amount);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testFundTransfer() throws InvalidInputException, InsufficientBalanceException 
	{
		service.createAccount("vamsi", "9440438111", new BigDecimal(1000));
		service.createAccount("krishna", "9849087543", new BigDecimal(1000));
		service.fundTransfer("9440438111", "9849087543", new BigDecimal(2000));
	}

}
