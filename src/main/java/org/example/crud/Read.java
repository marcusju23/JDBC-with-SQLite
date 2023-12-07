package org.example.crud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Read {

    public static void select(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("\n1. List ALL movies\n" +
                        "2. List ALL genres\n" +
                        "3. Back\n" +
                        "Choose an read option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> selectAllMovie();
                    case 2 -> selectAllGenre();
                    case 3 -> isRunning = false; // Exit the loop
                    default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
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
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // Loop through the result set
            System.out.printf("\n" + formatWidth, idHeader, titleHeader, directorHeader, priceHeader);
            while (rs.next()) {
                System.out.printf(formatWidth, rs.getInt("movieID"),
                        rs.getString("movieTitle"),
                        rs.getString("movieDirector"),
                        rs.getString("moviePrice"));
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
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // Loop through the result set
            System.out.printf("\n" + formatWidth, idHeader, genreHeader);
            while (rs.next()) {
                System.out.printf(formatWidth, rs.getInt("genreID"),
                        rs.getString("genreName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
