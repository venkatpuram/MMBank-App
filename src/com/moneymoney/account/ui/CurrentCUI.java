package com.moneymoney.account.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.moneymoney.account.CurrentAccount;
import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.CurrentAccountService;
import com.moneymoney.account.service.CurrentAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

public class CurrentCUI {

	private static CurrentAccountService currentAccountService = new CurrentAccountServiceImpl();
	private static Scanner scanner = new Scanner(System.in);
	public static void start() {
		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Current Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Current Account Details");
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
			acceptInput("CA");
			break;
		case 2:
			System.out.println("enter account number:");
			int accountNumber = scanner.nextInt();
			CurrentAccount currentaccount = null;
				try {
					currentaccount = currentAccountService
							.getAccountById(accountNumber);
				} catch (ClassNotFoundException | SQLException
						| AccountNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			updateAccount(currentaccount);
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
		case 9:
			showAllAccounts();
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
			System.out.println("6.Sort By Account Balance in descending order");
			int choose = scanner.nextInt();
			List<CurrentAccount> currentAccountsList = null;
			switch(choose){
			case 1:
				try {
					currentAccountsList = currentAccountService.sortByAccountHolderName();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(CurrentAccount savings : currentAccountsList){
					System.out.println(savings);
				}
				break;
				case 2:
				try {
					currentAccountsList=currentAccountService.sortByAccountHolderNameInDescendingOrder();
					for(CurrentAccount savings: currentAccountsList){
						System.out.println(savings);
						}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				case 3:
				try {
					currentAccountsList=currentAccountService.sortByAccountBalance();
					for(CurrentAccount savings: currentAccountsList){
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
						currentAccountsList = currentAccountService.sortByBalanceRange(minimumBalance,maximumBalance);
						for (CurrentAccount savingsAccount:currentAccountsList){
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
						currentAccountsList = currentAccountService.sortByBalanceRangeInDescendingOrder(minBalance,maxBalance);
						for (CurrentAccount savingsAccount:currentAccountsList){
							System.out.println(savingsAccount);
						}
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
					break;
				case 6:
					try {
						currentAccountsList=currentAccountService.sortByAccountBalanceInDescendingOrder();
						for(CurrentAccount savings: currentAccountsList){
							System.out.println(savings);
							}
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
						break;
			}
		}while(true);
	}

	private static void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			CurrentAccount senderCurrentAccount = currentAccountService
					.getAccountById(senderAccountNumber);
			CurrentAccount receiverCurrentAccount = currentAccountService
					.getAccountById(receiverAccountNumber);
			currentAccountService.fundTransfer(senderCurrentAccount,
					receiverCurrentAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void searchAccount(int choose) {
		CurrentAccount currentaccount = null;
		switch (choose) {
		case 1:
			CurrentAccount currentAccount = null;
			System.out.println("Enter Account Id to search:");
			int accountNumber = scanner.nextInt();
			try {
				currentAccount = currentAccountService
						.getAccountById(accountNumber);
				System.out.println(currentAccount);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			break;
		case 2:
			List<CurrentAccount> currentAccountList;
			System.out.println("Enter Account HolderName to search:");
			String holderName = scanner.next();
			try {
				currentAccountList = currentAccountService
						.searchAccountByHolderName(holderName);
				for (CurrentAccount savingsAccountOne : currentAccountList) {
					System.out.println(savingsAccountOne);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
					
		}
		
	}

	private static void updateAccount(CurrentAccount currentaccount) {
		System.out.println("Enter New Account Holder Name to Update");
		String name = scanner.next();
		currentaccount.getBankAccount().setAccountHolderName(name);
		try {
			currentAccountService.updateAccount(currentaccount);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("updated name is:" + name);
		
	}

	private static void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		CurrentAccount currentAccount = null;
		try {
			currentAccount = currentAccountService
					.getAccountById(accountNumber);
			currentAccountService.deposit(currentAccount, amount);
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
		CurrentAccount currentAccount = null;
		try {
			currentAccount = currentAccountService
					.getAccountById(accountNumber);
			currentAccountService.withdraw(currentAccount, amount);
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

	private static void showAllAccounts() {
		List<CurrentAccount> currentAccounts;
		try {
			currentAccounts = currentAccountService.getAllCurrentAccount();
			for (CurrentAccount currentAccount : currentAccounts) {
				System.out.println(currentAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	private static void checkBalance() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();

		try {
			currentAccountService.getAccountById(accountNumber);
			System.out.println("balance :"
					+ currentAccountService.checkBalance(accountNumber));
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
			CurrentAccount savingsAccount = currentAccountService
					.getAccountById(accountNumber);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			currentAccountService.deleteAccount(accountNumber);
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void acceptInput(String type) {
		if (type.equalsIgnoreCase("CA")) {
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
			System.out.println("Enter Over Draft Limit(type na for Zero ):");
			String odLimitStr = scanner.next();
			double odLimit=0.0;
			if (!odLimitStr.equalsIgnoreCase("na")) {
				odLimit = Double.parseDouble(odLimitStr);
			}
			createCurrentAccount(accountHolderName, accountBalance, odLimit);
		}
	}
	
	private static void createCurrentAccount(String accountHolderName,
			double accountBalance, double odLimit) {
		try {
			currentAccountService.createNewAccount(accountHolderName,
					accountBalance, odLimit);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
