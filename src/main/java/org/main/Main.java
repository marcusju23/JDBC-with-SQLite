package org.main;

import crud.Create;
import crud.Delete;
import crud.Read;
import crud.Update;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static Connection connect() {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.configure().directory("config").load();
        String databaseUrl = dotenv.get("DATABASE_URL");

        // SQLite's connection string
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            Display.menu();
            try {
                int action = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                switch (action) {
                    case 0 -> isRunning = false;
                    case 1 -> Create.userInsert(scanner);
                    case 2 -> Read.userSelect(scanner);
                    case 3 -> Update.userUpdate(scanner);
                    case 4 -> Delete.userDeleteMovie(scanner);
                    default -> System.out.println("Invalid action: " + action);
                }
                // Menu.pressEnterToContinue(scanner);

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
}
