package com.andrius;

import com.andrius.models.Room;
import com.andrius.models.Client;
import com.andrius.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public  class Hotel{
    private static List<Room> hotelRoomList = new ArrayList<>();
    public static List<Room> getHotelRoomList() {
        return hotelRoomList;
    }
    public static void generateRooms() {
        for(byte i = 1; i < 6; i++){
            Room roomInstance = new Room(i);
            Hotel.hotelRoomList.add(roomInstance);
        }
    }

    // 1st Option methods
    public static void registerNewGuest() {
        System.out.println("Enter guest's first name.");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine().trim();
        System.out.println("Enter guest's last name.");
        String lastName = scanner.nextLine().trim();
        newGuestConfirmation(firstName, lastName);
    }
    private static void newGuestConfirmation(String firstName, String lastName) {
        System.out.println("Is this correct: " + firstName + " " + lastName + "? Y/N/EXIT" );
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        switch (command) {
            case "Y":
                checkForEmptyRooms(firstName, lastName);
                break;
            case "N":
                registerNewGuest();
                break;
            case "EXIT":
                Menu.menu();
                break;
            default:
                break;
        }
    }
    private static void checkForEmptyRooms(String firstName, String lastName) {
        for(Room room: hotelRoomList) {
            if (room.getRoomStatus() == 0) {
                room.checkinClient(firstName, lastName);
                break;
            }
        }
        System.out.println("Sorry, seems like all the rooms are currently taken.");
        Util.consolePause();
    }
    // 2nd Option methods
    public static void getDetails() {
        System.out.println("Please enter guest's first name.");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine().trim();
        System.out.println("Now enter guest's last name.");
        String lastName = scanner.nextLine().trim();
        findAndCheckoutGuest(firstName, lastName);
    }
    private static void findAndCheckoutGuest(String firstName, String lastName) {
        hotelRoomList.forEach(room -> {
            room.roomHistory.forEach(client -> {
                if (Objects.equals(client.firstName, firstName) && Objects.equals(client.lastName, lastName)) {
                    room.checkoutClient();
                    System.out.println(firstName + " " + lastName + " has been checked out of room " + room.getRoomNumber() + ".");
                    Util.consolePause();
                }
            });
        });
        System.out.println("Sorry, no one with the name "+ firstName + " " + lastName + " was found.");
        Util.consolePause();
    }
    // 3rd Option methods
    public static void checkRoomAvailability() {
        hotelRoomList.forEach(room -> {
            if(room.getRoomStatus() == 0) {
                System.out.println("Room number " + room.getRoomNumber() + " is free.");
            } else {
                Client client = room.roomHistory.get(room.roomHistory.size()-1);
                System.out.println(
                    "Room number " + room.getRoomNumber() + " is taken by " + client.firstName + " " + client.lastName
                );
            }
        });
        Util.consolePause();
    }
    // 4th Option methods
    public static void getRoomStatus() {
        System.out.println("Please enter the number of the room you'd like to check out in detail." );
        Scanner scanner = new Scanner(System.in);
        do
            try {
                String command = scanner.nextLine();
                byte roomNumber = Byte.parseByte(command);
                if(roomNumber > 0 || roomNumber < 6) {
                    hotelRoomList.get(roomNumber - 1).getRoomInfo();
                }
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid room number.");
            }
        while (true);
    }
}
