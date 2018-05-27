package com.andrius;

import java.util.Scanner;

public class Menu {
    public static void menu() {
        System.out.println("Greetings, commands are listed below:");
        System.out.println();
        System.out.println("To check in a client please type \"Room\" and follow instructions.");
        System.out.println("To check out a client please type \"Check out\" and follow instructions.");
        System.out.println("To check room availability please type \"Room availability\".");
        System.out.println("To check room status please type \"Room status\" and follow instructions.");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        switch (command) {
            case "Room":
                Hotel.registerNewGuest();
                menu();
                break;
            case "Check out":
                Hotel.getDetails();
                menu();
                break;
            case "Room availability":
                Hotel.checkRoomAvailability();
                menu();
                break;
            case "Room status":
                Hotel.getRoomStatus();
                menu();
                break;
            default:
                menu();
                break;
        }
    }
}
