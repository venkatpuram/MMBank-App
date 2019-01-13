package com.moneymoney.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import com.moneymoney.account.ui.AccountCUI;
import com.moneymoney.account.ui.CurrentCUI;

public class Bootstrap {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM ACCOUNT");
			preparedStatement.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("****** Welcome to Money Money Bank********");
		System.out.println("1. Savings Account");
		System.out.println("2. Current Account");
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			AccountCUI.start();
			break;
		case 2:
			CurrentCUI.start();
			break;
		}

	}

}
