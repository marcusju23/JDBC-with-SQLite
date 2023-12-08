package org.example.crud;

import org.example.Display;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Create {

    public static void insertMenu(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("\n1. Add movie\n" +
                        "2. Add genre\n" +
                        "3. Back\n" +
                        "Choose an alternative: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> {
                        userInsertMovie(scanner);
                        Display.pressEnterToContinue(scanner);
                    }
                    case 2 -> {
                        userInsertGenre(scanner);
                        Display.pressEnterToContinue(scanner);
                    }
                    case 3 -> isRunning = false; // Exit the loop
                    default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    private static void insertMovie(String title, String director, int price, int genre) {
        String sql = "INSERT INTO movie(movieTitle, movieDirector, moviePrice, genreID) VALUES(?,?,?,?)";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, director);
            preparedStatement.setInt(3, price);
            preparedStatement.setInt(4, genre);
            preparedStatement.executeUpdate();
            System.out.println("You have added a new movie: ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void userInsertMovie(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Title: ");
                String title = scanner.nextLine();

                System.out.print("Director: ");
                String author = scanner.nextLine();

                System.out.print("Price: ");
                int price = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after reading the int

                Read.selectAllGenre();
                System.out.print("Genre ID: ");
                int genreID = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after reading the int
                insertMovie(title, author, price, genreID);
                System.out.println(title + ", " + author + ", " + price + ", " + genreID);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid datatype.");
                scanner.nextLine(); // Consume the invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                break;
            }
        }
    }

    private static void insertGenre(String genre) {
        String sql = "INSERT INTO genre(genreName) VALUES(?)";
        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genre);
            preparedStatement.executeUpdate();
            System.out.println("You have added a new genre: " + genre);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void userInsertGenre(Scanner scanner) {
        while (true) {
            try {
                System.out.print("New genre: ");
                String newGenre = scanner.nextLine();
                insertGenre(newGenre);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid datatype.");
                scanner.nextLine(); // Consume the invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                break;
            }
        }
    }
}