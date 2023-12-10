package org.example.crud;

import org.example.Display;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Update {

    private static final ArrayList<String> movieColumns = new ArrayList<>(Arrays.asList(
            "movieID", "movieTitle", "movieDirector", "moviePrice", "genreID"));

    public static void updateMenu(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("\n1. Update movie\n" +
                        "2. Update genre\n" +
                        "3. Back\n" +
                        "Choose an alternative: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> {
                        userUpdateMovie(scanner);
                        Display.pressEnterToContinue(scanner);
                    }
                    case 2 -> {
                        userUpdateGenre(scanner);
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

    private static void updateMovie(String columnName, Object columnValue, int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connect();
            String sql = "UPDATE movie SET " + columnName + " = ? WHERE movieID = ?";
            preparedStatement = connection.prepareStatement(sql);

            // set the corresponding params based on the type of columnValue
            if (columnValue instanceof String) {
                preparedStatement.setString(1, (String) columnValue);
            } else if (columnValue instanceof Integer) {
                preparedStatement.setInt(1, (Integer) columnValue);
            } else {
                throw new IllegalArgumentException("Unsupported data type: " + columnValue.getClass().getSimpleName());
            }

            preparedStatement.setInt(2, id);

            // update
            preparedStatement.executeUpdate();
            System.out.println("You have updated " + columnName + " to " + columnValue + " at ID: " + id);

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }

    private static void userUpdateMovie(Scanner scanner) {
        boolean validColumnChoice = false;
        String columnName = null;
        while (!validColumnChoice) {
            try {
                Display.movieColumns();
                int userChoice = scanner.nextInt();
                scanner.nextLine(); // consume the newline character

                // Validate user input
                if (userChoice >= 1 && userChoice <= movieColumns.size()) {
                    columnName = getColumnAtIndex(movieColumns, userChoice - 1);

                    if (columnName != null) {
                        validColumnChoice = true;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("Invalid choice. " +
                            "Please enter a number between 1 and " + movieColumns.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // consume the invalid input
            }
        }
        try {
            Read.selectMovieAndGenre();
            System.out.print("\nSelect ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Update " + columnName + " to: ");
            String columnValue = scanner.nextLine();
            if (columnValue.equals("moviePrice") || columnValue.equals("movieID") || columnValue.equals("genreID")) {
                int columnNameInt = Integer.parseInt(columnValue);
                updateMovie(columnName, columnNameInt, id);
            } else
                updateMovie(columnName, columnValue, id);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer for the ID.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    private static String getColumnAtIndex(ArrayList<String> list, int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        } else {
            return null;
        }
    }

    private static void updateGenre(String genre, int id) {
        Connection connection = null;

        try {
            String sql = "UPDATE genre SET genreName = ? WHERE genreID = ?";
            connection = connect();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, genre);
                preparedStatement.setInt(2, id);

                // update
                preparedStatement.executeUpdate();
                System.out.println("New genre name: " + genre + " at ID: " + id);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    private static void userUpdateGenre(Scanner scanner) {
        Read.selectAllGenre();
        int id = 0;
        boolean isValidId = false;
        while (!isValidId) {
            try {
                System.out.print("\nSelect ID to update: ");
                id = scanner.nextInt();
                isValidId = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer for the ID.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        scanner.nextLine(); // Consume the newline character

        System.out.print("New genre: ");
        String genre = scanner.next();
        updateGenre(genre, id);
    }
}