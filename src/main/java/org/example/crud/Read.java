package org.example.crud;

import org.example.Display;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Read {

    public static void selectMenu(Scanner scanner) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print("\n1. All movies\n" +
                        "2. All genres\n" +
                        "3. Average movie price (rounded)\n" +
                        "4. Movies and Genres (join)\n" +
                        "5. Amount of movies in each genre (join)\n" +
                        "6. Back\n" +
                        "Choose an read option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> {
                        selectAllMovie();
                        Display.pressEnterToContinue(scanner);
                    }
                    case 2 -> {
                        selectAllGenre();
                        Display.pressEnterToContinue(scanner);
                    }
                    case 3 -> {
                        selectMovieAvgPrice();
                        Display.pressEnterToContinue(scanner);
                    }
                    case 4 -> {
                        selectMovieAndGenre();
                        Display.pressEnterToContinue(scanner);
                    }
                    case 5 -> {
                        selectCountMovieInGenre();
                        Display.pressEnterToContinue(scanner);
                    }
                    case 6 -> isRunning = false; // Exit the loop
                    default -> System.out.println("Invalid choice. Please enter number between 1-5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public static void selectMovieAndGenre() {
        String sql = "SELECT movie.movieID, movie.movieTitle, movie.movieDirector, " +
                "genre.genreID, genre.genreName, movie.moviePrice " +
                "FROM movie JOIN genre ON movie.genreID = genre.genreID";

        // Column headers
        String idHeader = "ID:";
        String titleHeader = "Title:";
        String directorHeader = "Director:";
        String genreIDHeader = "GID:";
        String genreHeader = "Genre:";
        String priceHeader = "Price:";
        String formatWidth = "%-10s%-60s%-30s%-10s%-30s%s%n"; // Align columns

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            System.out.printf("\n" + formatWidth, idHeader, titleHeader,
                    directorHeader, genreIDHeader, genreHeader, priceHeader);
            while (resultSet.next()) {
                System.out.printf(formatWidth, resultSet.getInt("movieID"),
                        resultSet.getString("movieTitle"),
                        resultSet.getString("movieDirector"),
                        resultSet.getString("genreID"),
                        resultSet.getString("genreName"),
                        resultSet.getString("moviePrice"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
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

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            System.out.printf("\n" + formatWidth, idHeader, titleHeader, directorHeader, priceHeader);
            while (resultSet.next()) {
                System.out.printf(formatWidth, resultSet.getInt("movieID"),
                        resultSet.getString("movieTitle"),
                        resultSet.getString("movieDirector"),
                        resultSet.getString("moviePrice"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void selectAllGenre() {
        String sql = "SELECT * FROM genre";

        // Column headers
        String idHeader = "ID:";
        String genreHeader = "Genre:";
        String formatWidth = "%-10s%-10s%n"; // Align columns

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            System.out.printf("\n" + formatWidth, idHeader, genreHeader);
            while (resultSet.next()) {
                System.out.printf(formatWidth, resultSet.getInt("genreID"),
                        resultSet.getString("genreName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void selectMovieAvgPrice() {
        String sql = "SELECT CAST(AVG(moviePrice) AS INTEGER) AS average_price FROM movie";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int averagePrice = resultSet.getInt("average_price");
                System.out.print("\nAverage movie price (rounded): " + averagePrice);
            } else {
                System.out.println("No results found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void selectCountMovieInGenre() {
        String sql = "SELECT genre.genreID, genre.genreName, COUNT(movie.movieID) AS movieCount " +
                "FROM genre " +
                "LEFT JOIN movie ON genre.genreID = movie.genreID " +
                "GROUP BY genre.genreID, genre.genreName";

        // Column headers
        String genreIdHeader = "ID:";
        String genreHeader = "Genre:";
        String movieCountHeader = "Movies:";
        String formatWidth = "%-10s%-45s%-45s%n";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            System.out.printf("\n" + formatWidth, genreIdHeader, genreHeader, movieCountHeader);
            while (resultSet.next()) {
                int genreID = resultSet.getInt("genreID");
                String genreName = resultSet.getString("genreName");
                int movieCount = resultSet.getInt("movieCount");

                System.out.printf(formatWidth, genreID, genreName, movieCount);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}