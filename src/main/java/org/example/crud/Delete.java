package org.example.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static org.example.Main.connect;

public class Delete {

    public static void delete(int id) {
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

    public static void userDeleteMovie(Scanner scanner) {
        Read.selectAllMovie();
        System.out.println("Enter ID of the movie you wish to delete: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
    }
}
