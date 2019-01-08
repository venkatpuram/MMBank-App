package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	private static SavingsAccountService savingsAccountService = new SavingsAccountServiceImpl();

	public static void start() {

		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");

			int choice = scanner.nextInt();

			performOperation(choice);

		} while (true);
	}

	private static void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;
		case 2:
			System.out.println("enter account number:");
			int accountNumber = scanner.nextInt();
			SavingsAccount savingsaccount = null;
			try {
				savingsaccount = savingsAccountService
						.getAccountById(accountNumber);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("*****Update Account*****");
			System.out.println("1. Update Account Holder Name");
			System.out.println("2. Update Salaried Type");
			System.out.println("3. Update Account Holder Name and Salary Type");
			int select = scanner.nextInt();

			try {
				updateAccountTypes(select, savingsaccount);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 3:
			deleteAccount();
			break;
		case 4:
			System.out.println("1. Search account by account_id");
			System.out.println("2. Search account by Account Holder Name");
			int choose = scanner.nextInt();
			searchAccount(choose);
			break;
		case 9:
			showAllAccounts();
			break;
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			checkBalance();
			break;
		case 10:
			sortAccounts();
			break;
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}

	}

	private static void sortAccounts() {
		do{
			System.out.println("1.Sort By Account Holder Name");
			System.out.println("2.Sort By Account Holder Name in descending order");
			System.out.println("3.Sort By Account Balance");
			System.out.println("4.Enter account balance range to sort in ascending order of the balance");
			System.out.println("5.Enter account balance range to sort in descending order of the balance");
			int choose = scanner.nextInt();
			List<SavingsAccount> savingsAccountsList = null;
			switch(choose){
			case 1:
				try {
					savingsAccountsList = savingsAccountService.sortByAccountHolderName();
					for(SavingsAccount savings : savingsAccountsList){
						System.out.println(savings);
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;
				case 2:
				try {
					savingsAccountsList=savingsAccountService.sortByAccountHolderNameInDescendingOrder();
					for(SavingsAccount savings: savingsAccountsList){
						System.out.println(savings);
						}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				case 3:
				try {
					savingsAccountsList=savingsAccountService.sortByAccountBalance();
					for(SavingsAccount savings: savingsAccountsList){
						System.out.println(savings);
						}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}						
					break;
				case 4:
					System.out.println("Enter minimun range");
					int minimumBalance = scanner.nextInt();
					System.out.println("Enter maximum range");
					int maximumBalance = scanner.nextInt();
					try {
						savingsAccountsList = savingsAccountService.sortByBalanceRange(minimumBalance,maximumBalance);
						for (SavingsAccount savingsAccount:savingsAccountsList){
							System.out.println(savingsAccount);
						}
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case 5:
					System.out.println("Enter minimun range");
					int minBalance = scanner.nextInt();
					System.out.println("Enter maximum range");
					int maxBalance = scanner.nextInt();
					try {
						savingsAccountsList = savingsAccountService.sortByBalanceRangeInDescendingOrder(minBalance,maxBalance);
						for (SavingsAccount savingsAccount:savingsAccountsList){
							System.out.println(savingsAccount);
						}
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
			}
		}while(true);
	}

	private static void searchAccount(int choose) {
		SavingsAccount savingsaccount = null;
		switch (choose) {
		case 1:
			SavingsAccount savingsAccount = null;
			System.out.println("Enter Account Id to search:");
			int accountNumber = scanner.nextInt();
			try {
				savingsAccount = savingsAccountService
						.getAccountById(accountNumber);
				System.out.println(savingsAccount);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			break;
		case 2:
			List<SavingsAccount> savingsAccountList;
			System.out.println("Enter Account HolderName to search:");
			String holderName = scanner.next();
			try {
				savingsAccountList = savingsAccountService
						.searchAccountByHolderName(holderName);
				for (SavingsAccount savingsAccountOne : savingsAccountList) {
					System.out.println(savingsAccountOne);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
			
			
		}

	}

	private static void updateAccountTypes(int select,
			SavingsAccount savingsaccount) throws ClassNotFoundException,
			SQLException {
		switch (select) {
		case 1:
			System.out.println("Enter New Account Holder Name to Update");
			String name = scanner.next();
			savingsaccount.getBankAccount().setAccountHolderName(name);
			savingsAccountService.updateAccount(savingsaccount);
			System.out.println("updated name is:" + name);
			break;
		case 2:
			System.out
					.println("If you want to change your salary type to unsalaried enter (n)");
			System.out
					.println("If you want to change your unsalary type to salaried enter (y)");

			boolean type = scanner.next().equalsIgnoreCase("n") ? false : true;
			savingsaccount.setSalary(type);
			savingsAccountService.updateAccount(savingsaccount);
			break;
		case 3:
			System.out.println("Enter New Account Holder Name to Update");
			String name1 = scanner.next();
			System.out
					.println("If you want to change your salary type to unsalaried enter (n)");
			System.out
					.println("If you want to change your unsalary type to salaried enter (y)");

			boolean type1 = scanner.next().equalsIgnoreCase("n") ? false : true;
			savingsaccount.getBankAccount().setAccountHolderName(name1);
			savingsaccount.setSalary(type1);
			savingsAccountService.updateAccount(savingsaccount);
			break;

		}
	}

	private static void checkBalance() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();

		try {
			savingsAccountService.getAccountById(accountNumber);
			System.out.println("balance :"
					+ savingsAccountService.checkBalance(accountNumber));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void deleteAccount() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();

		try {
			SavingsAccount savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deleteAccount(accountNumber);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService
					.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService
					.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount,
					receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService
					.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void sortMenu(String sortWay) {
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");

			int choice = scanner.nextInt();

		} while (true);

	}

	private static void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	private static void acceptInput(String type) {
		if (type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out
					.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance = 0.0;
			if (!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n") ? false
					: true;
			createSavingsAccount(accountHolderName, accountBalance, salary);
		}
	}

	private static void createSavingsAccount(String accountHolderName,
			double accountBalance, boolean salary) {
		try {
			savingsAccountService.createNewAccount(accountHolderName,
					accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
