package com.moneymoney.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.exception.AccountNotFoundException;

public interface CurrentAccountDAO {

	CurrentAccount createNewAccount(CurrentAccount account) throws SQLException, ClassNotFoundException;

	CurrentAccount getAccountById(int accountNumber) throws SQLException, ClassNotFoundException, AccountNotFoundException;

	CurrentAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	double checkBalance(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<CurrentAccount> getAllCurrentAccount() throws ClassNotFoundException, SQLException;

	void updateBalance(int accountNumber, double currentBalance, double odLimit) throws ClassNotFoundException, SQLException;

	CurrentAccount updateAccount(CurrentAccount currentaccount) throws SQLException, ClassNotFoundException;

	List<CurrentAccount> searchAccountByHolderName(String holderName) throws SQLException, ClassNotFoundException;

	List<CurrentAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByAccountHolderNameInDescendingOrder() throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByBalanceRange(int minimumBalance,
			int maximumBalance) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByBalanceRangeInDescendingOrder(int minBalance,
			int maxBalance) throws ClassNotFoundException, SQLException;

	List<CurrentAccount> sortByAccountBalanceInDescendingOrder() throws SQLException, ClassNotFoundException;

}