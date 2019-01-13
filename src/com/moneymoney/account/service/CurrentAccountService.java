package com.moneymoney.account.service;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface CurrentAccountService {

	CurrentAccount createNewAccount(String accountHolderName, double accountBalance,
			double odLimit) throws ClassNotFoundException, SQLException;

	CurrentAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	double checkBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException;

	void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException;

	void deposit(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException;

	CurrentAccount updateAccount(CurrentAccount currentaccount) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> searchAccountByHolderName(String holderName) throws SQLException, ClassNotFoundException;

	void fundTransfer(CurrentAccount sender,
			CurrentAccount receiver, double amount) throws SQLException;

	List<CurrentAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByAccountHolderNameInDescendingOrder() throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByBalanceRange(int minimumBalance,
			int maximumBalance) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByBalanceRangeInDescendingOrder(int minBalance,
			int maxBalance) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByAccountBalanceInDescendingOrder() throws ClassNotFoundException, SQLException;

}