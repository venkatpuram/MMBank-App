package com.moneymoney.account.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class CurrentAccountDAOImpl implements CurrentAccountDAO {

	@Override
	public CurrentAccount createNewAccount(CurrentAccount account)
			throws SQLException, ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
		preparedStatement
				.setInt(1, account.getBankAccount().getAccountNumber());
		preparedStatement.setString(2, account.getBankAccount()
				.getAccountHolderName());
		preparedStatement.setDouble(3, account.getBankAccount()
				.getAccountBalance());
		preparedStatement.setObject(4, false);
		preparedStatement.setDouble(5, account.getOdLimit());
		preparedStatement.setString(6, "CA");
		preparedStatement.executeUpdate();
		preparedStatement.close();
		DBUtil.commit();
		return account;
	}

	@Override
	public CurrentAccount getAccountById(int accountNumber)
			throws SQLException, ClassNotFoundException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		CurrentAccount currentAccount = null;
		if (resultSet.next()) {
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			currentAccount = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			return currentAccount;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");
	}

	@Override
	public CurrentAccount deleteAccount(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		CurrentAccountDAO currentAccountDao = new CurrentAccountDAOImpl();
		if (currentAccountDao.getAccountById(accountNumber).getBankAccount()
				.getAccountNumber() == accountNumber) {
			Connection connection = DBUtil.getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM account where account_id=?");
			preparedStatement.setInt(1, accountNumber);
			preparedStatement.execute();
			DBUtil.commit();
			return null;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");

	}

	@Override
	public double checkBalance(int accountNumber)
			throws ClassNotFoundException, SQLException,
			AccountNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT account_bal FROM account where account_id=?");
		preparedStatement.setInt(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			double accountBalance = resultSet.getDouble(1);
			return accountBalance;
		}
		throw new AccountNotFoundException("Account with account number "
				+ accountNumber + " does not exist.");

	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount()
			throws ClassNotFoundException, SQLException {
		List<CurrentAccount> currentAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
		while (resultSet.next()) {// Check if row(s) is present in table
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			CurrentAccount currentAccount = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			currentAccounts.add(currentAccount);
		}
		DBUtil.commit();
		return currentAccounts;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance, double odLimit)
			throws ClassNotFoundException, SQLException {
		Connection connection = DBUtil.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement preparedStatement = connection
				.prepareStatement("UPDATE ACCOUNT SET account_bal=?,od_limit=? where account_id=?");
		preparedStatement.setDouble(1, currentBalance);
		preparedStatement.setDouble(2, odLimit);
		preparedStatement.setInt(3, accountNumber);
		preparedStatement.executeUpdate();
	}

	@Override
	public CurrentAccount updateAccount(CurrentAccount currentaccount) throws SQLException, ClassNotFoundException {
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement
				("UPDATE ACCOUNT SET account_hn=? where account_id=?");
		preparedStatement.setString(1, currentaccount.getBankAccount().getAccountHolderName());
		preparedStatement.setInt(2, currentaccount.getBankAccount().getAccountNumber());
		preparedStatement.executeUpdate();
		DBUtil.commit();
		return currentaccount;
	}

	@Override
	public List<CurrentAccount> searchAccountByHolderName(String holderName) throws SQLException, ClassNotFoundException {
		List<CurrentAccount> currentAccounts = new ArrayList<>();
		Connection connection = DBUtil.getConnection();
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT * FROM account WHERE account_hn=?");
		preparedStatement.setString(1, holderName);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			CurrentAccount currentAccount = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			currentAccounts.add(currentAccount);
		}
		DBUtil.commit();
		return currentAccounts;
	}

	@Override
	public List<CurrentAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException {
		List<CurrentAccount> currentAccount=new ArrayList<>();
		Connection connection=DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_hn");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			CurrentAccount currentAccountList = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			currentAccount.add(currentAccountList);
		}
		DBUtil.commit();
		return currentAccount;
	}

	@Override
	public List<CurrentAccount> sortByAccountHolderNameInDescendingOrder() throws ClassNotFoundException, SQLException{
		List<CurrentAccount> currentAccount=new ArrayList<>();
		Connection connection=DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_hn DESC");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			CurrentAccount currentAccountList = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			currentAccount.add(currentAccountList);
		}
		DBUtil.commit();
		return currentAccount;
	}

	@Override
	public List<CurrentAccount> sortByAccountBalance() throws ClassNotFoundException, SQLException{
		List<CurrentAccount> currentAccount=new ArrayList<>();
		Connection connection=DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_bal");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			CurrentAccount currentAccountList = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			currentAccount.add(currentAccountList);
		}
		DBUtil.commit();
		return currentAccount;
	}

	@Override
	public List<CurrentAccount> sortByBalanceRange(int minimumBalance,
			int maximumBalance)	throws ClassNotFoundException, SQLException {
			List<CurrentAccount> currentAccount=new ArrayList<>();
			Connection connection=DBUtil.getConnection();
			PreparedStatement preparedStatementQuery = connection.prepareStatement("SELECT * FROM account WHERE account_bal BETWEEN ? and ? ORDER BY account_bal");
			preparedStatementQuery.setDouble(1, minimumBalance);
			preparedStatementQuery.setDouble(2, maximumBalance);
			ResultSet resultSet = preparedStatementQuery.executeQuery();
			while(resultSet.next())
			{
				int accountNumber = resultSet.getInt(1);
				String accountHolderName = resultSet.getString("account_hn");
				double accountBalance = resultSet.getDouble(3);
				double odLimit = resultSet.getDouble(5);
				CurrentAccount currentAccountList = new CurrentAccount(accountNumber,
						accountHolderName, accountBalance, odLimit);
				currentAccount.add(currentAccountList);
			}
			DBUtil.commit();
			return currentAccount;
	}

	@Override
	public List<CurrentAccount> sortByBalanceRangeInDescendingOrder(
			int minBalance, int maxBalance) throws ClassNotFoundException, SQLException{
				List<CurrentAccount> currentAccount=new ArrayList<>();
				Connection connection=DBUtil.getConnection();
				PreparedStatement preparedStatementQuery = connection.prepareStatement("SELECT * FROM account WHERE account_bal BETWEEN ? and ? ORDER BY account_bal DESC");
				preparedStatementQuery.setDouble(1, minBalance);
				preparedStatementQuery.setDouble(2, maxBalance);
				ResultSet resultSet = preparedStatementQuery.executeQuery();
				while(resultSet.next())
				{
					int accountNumber = resultSet.getInt(1);
					String accountHolderName = resultSet.getString("account_hn");
					double accountBalance = resultSet.getDouble(3);
					double odLimit = resultSet.getDouble(5);
					CurrentAccount currentAccountList = new CurrentAccount(accountNumber,
							accountHolderName, accountBalance, odLimit);
					currentAccount.add(currentAccountList);
				}
				DBUtil.commit();
				return currentAccount;
	}

	@Override
	public List<CurrentAccount> sortByAccountBalanceInDescendingOrder() throws SQLException, ClassNotFoundException {
		List<CurrentAccount> currentAccount=new ArrayList<>();
		Connection connection=DBUtil.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM account ORDER BY account_bal DESC");
		while(resultSet.next())
		{
			int accountNumber = resultSet.getInt(1);
			String accountHolderName = resultSet.getString("account_hn");
			double accountBalance = resultSet.getDouble(3);
			double odLimit = resultSet.getDouble(5);
			CurrentAccount currentAccountList = new CurrentAccount(accountNumber,
					accountHolderName, accountBalance, odLimit);
			currentAccount.add(currentAccountList);
		}
		DBUtil.commit();
		return currentAccount;
	}

}
