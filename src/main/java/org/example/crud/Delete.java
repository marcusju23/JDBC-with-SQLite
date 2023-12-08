package org.example.crud;

import org.example.Display;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Delete {

    public static void deleteMenu(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("\n1. Delete movie\n" +
                        "2. Delete genre\n" +
                        "3. Back\n" +
                        "Choose an delete option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> {
                        userDeleteMovie(scanner);
                        Display.pressEnterToContinue(scanner);
                    }
                    case 2 -> {
                        userDeleteGenre(scanner);
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

    private static void deleteMovie(int id) {
        String sqlDelete = "DELETE FROM movie WHERE movieID = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {

            // set the corresponding param
            preparedStatement.setInt(1, id);
            // execute the delete statement
            preparedStatement.executeUpdate();
            System.out.println("You have removed moveID: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void userDeleteMovie(Scanner scanner) {
        Read.selectMovieAndGenre();
        System.out.println("\nEnter ID of the movie you wish to delete: ");
        int inputId = scanner.nextInt();
        deleteMovie(inputId);
        scanner.nextLine();
    }

    private static void deleteGenre(int id) {
        String sqlDelete = "DELETE FROM genre WHERE genreID = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {

            // set the corresponding param
            preparedStatement.setInt(1, id);
            // execute the delete statement
            preparedStatement.executeUpdate();
            System.out.println("You have removed genreID: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void userDeleteGenre(Scanner scanner) {
        Read.selectAllGenre();
        System.out.println("\nEnter ID of the genre you wish to delete: ");
        int inputId = scanner.nextInt();
        deleteGenre(inputId);
        scanner.nextLine();
    }
}
