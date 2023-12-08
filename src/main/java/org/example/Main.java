package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.crud.Create;
import org.example.crud.Delete;
import org.example.crud.Read;
import org.example.crud.Update;

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
                    case 1 -> Create.insertMenu(scanner);
                    case 2 -> Read.selectMenu(scanner);
                    case 3 -> Update.updateMenu(scanner);
                    case 4 -> Delete.deleteMenu(scanner);
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
