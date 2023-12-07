package org.main;

import java.util.Scanner;

public class Display {

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\nPress Enter to Continue...\n");
        scanner.nextLine(); // Wait for the user to press Enter
    }

    public static void menu() {
        System.out.print("\n-----MAIN MENU-----\n" +
                "1  - Add \n" +
                "2  - Show\n" +
                "3  - Update\n" +
                "4  - Remove\n" +
                "0  - Quit\n\n" +
                "Enter one of the menu alternatives: ");
    }

    public static void movieColumns() {
        System.out.print("\n-----Movie Columns-----\n" +
                "1  - ID\n" +
                "2  - Title\n" +
                "3  - Director\n" +
                "4  - Price\n\n" +
                "Enter one of the menu alternatives: ");
    }
}
