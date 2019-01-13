package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.dao.CurrentAccountDAO;
import com.moneymoney.account.dao.CurrentAccountDAOImpl;
import com.moneymoney.account.factory.AccountFactory;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;
import com.moneymoney.exception.InsufficientFundsException;
import com.moneymoney.exception.InvalidInputException;

public class CurrentAccountServiceImpl implements CurrentAccountService {

	private AccountFactory factory;
	private CurrentAccountDAO currentAccountDAO;

	public CurrentAccountServiceImpl() {
		factory = AccountFactory.getInstance();
		currentAccountDAO = new CurrentAccountDAOImpl();
	}
	
	@Override
	public CurrentAccount createNewAccount(String accountHolderName,
			double accountBalance, double odLimit) throws ClassNotFoundException, SQLException {
		CurrentAccount account = factory.createNewCurrentAccount(accountHolderName, accountBalance, odLimit);
		currentAccountDAO.createNewAccount(account);
		return null;
		
	}

	@Override
	public CurrentAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.getAccountById(accountNumber);
	}

	@Override
	public CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.deleteAccount(accountNumber);
		
	}

	@Override
	public double checkBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.checkBalance(accountNumber);
	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.getAllCurrentAccount();
	}

	@Override
	public void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		double odLimit=account.getOdLimit();
		if (amount > 0 && (currentBalance+odLimit) >= amount) {
			currentBalance -= amount;
			currentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance, odLimit);
			
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
		}
		
	}

	@Override
	public void deposit(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			double odLimit=account.getOdLimit();
			currentBalance += amount;
			currentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance, odLimit);
			//savingsAccountDAO.commit();
		}else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
		
	}

	@Override
	public CurrentAccount updateAccount(CurrentAccount currentaccount) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.updateAccount(currentaccount);
	}

	@Override
	public List<CurrentAccount> searchAccountByHolderName(String holderName) throws SQLException, ClassNotFoundException {
		return currentAccountDAO.searchAccountByHolderName(holderName);
	}

	@Override
	public void fundTransfer(CurrentAccount sender,
			CurrentAccount receiver, double amount) throws SQLException {
		try {
			withdraw(sender, amount);
			deposit(receiver, amount);
			DBUtil.commit();
		} catch (InvalidInputException | InsufficientFundsException e) {
			e.printStackTrace();
			DBUtil.rollback();
		} catch(Exception e) {
			e.printStackTrace();
			DBUtil.rollback();
		}
		
	}

	@Override
	public List<CurrentAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByAccountHolderName();
	}

	@Override
	public List<CurrentAccount> sortByAccountHolderNameInDescendingOrder() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByAccountHolderNameInDescendingOrder();
	}

	@Override
	public List<CurrentAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByAccountBalance();
	}

	@Override
	public List<CurrentAccount> sortByBalanceRange(int minimumBalance,
			int maximumBalance) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByBalanceRange(minimumBalance,maximumBalance);
	}

	@Override
	public List<CurrentAccount> sortByBalanceRangeInDescendingOrder(
			int minBalance, int maxBalance) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByBalanceRangeInDescendingOrder(minBalance,maxBalance);
	}

	@Override
	public List<CurrentAccount> sortByAccountBalanceInDescendingOrder() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByAccountBalanceInDescendingOrder();
	}

}
