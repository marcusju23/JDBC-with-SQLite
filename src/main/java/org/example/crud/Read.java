package org.example.crud;

import org.example.Display;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Read {

    public static void select(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("\n1. All movies\n" +
                        "2. All genres\n" +
                        "3. Average movie price (rounded)\n" +
                        "4. Average genre\n" +
                        "5. Back\n" +
                        "Choose an read option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> selectAllMovie();
                    case 2 -> selectAllGenre();
                    case 3 -> {
                        selectMovieAvgPrice();
                        Display.pressEnterToContinue(scanner);
                    }
                    case 4 -> selectAllGenre();
                    case 5 -> isRunning = false; // Exit the loop
                    default -> System.out.println("Invalid choice. Please enter number between 1-5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public static void selectAllMovie() {
        String sql = "SELECT * FROM movie";

        // Column headers
        String idHeader = "ID:";
        String titleHeader = "Title:";
        String directorHeader = "Director:";
        String priceHeader = "Price:";
        String formatWidth = "%-10s%-60s%-30s%s%n"; // Align columns

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.printf("\n" + formatWidth, idHeader, titleHeader, directorHeader, priceHeader);
            while (resultSet.next()) {
                System.out.printf(formatWidth, resultSet.getInt("movieID"),
                        resultSet.getString("movieTitle"),
                        resultSet.getString("movieDirector"),
                        resultSet.getString("moviePrice"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectAllGenre() {
        String sql = "SELECT * FROM genre";

        // Column headers
        String idHeader = "ID:";
        String genreHeader = "Genre:";
        String formatWidth = "%-10s%-10s%n"; // Align columns

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.printf("\n" + formatWidth, idHeader, genreHeader);
            while (resultSet.next()) {
                System.out.printf(formatWidth, resultSet.getInt("genreID"),
                        resultSet.getString("genreName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void selectMovieAvgPrice() {
        String sql = "SELECT CAST(AVG(moviePrice) AS INTEGER) AS average_price FROM movie";

        try {
            Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int averagePrice = resultSet.getInt("average_price");
                System.out.println("\nAverage movie price (rounded): " + averagePrice);
            } else {
                System.out.println("No results found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
