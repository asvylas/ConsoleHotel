package com.andrius.utils;

import com.andrius.Menu;
import com.andrius.models.Client;
import com.andrius.models.Room;
import com.andrius.Hotel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;

public class Util {
    private static List<String> dataClientList = new ArrayList<>();
    private static List<Byte> dataRoomStatus = new ArrayList<>();

    public static void initiateHotel(){
        if (new File("storedData.txt").isFile()) {
            Util.readFromStoredData();
        } else {
            Util.createStoredData();
        }
    }

    public static void createStoredData() {
        try {
            PrintWriter writer = new PrintWriter("storedData.txt", "UTF-8");
            for (int i = 0; i < 5; i++) {
                writer.println("");
                writer.println("0");
            }
            writer.close();
            readFromStoredData();
        } catch (Exception ex) {
            System.out.println("Unable to create file for data storing...");
            consolePause();
        }
    }
    public static void spreadStoredData() {
        List<Room> roomList = Hotel.getHotelRoomList();
        for (int i = 0; i < roomList.size(); i++) {
            roomList.get(i).setRoomStatus(dataRoomStatus.get(i));
        }
        for (int i = 0; i < roomList.size(); i++) {
            String clients[] = dataClientList.get(i).split(";");
            for (String s : clients) {
                if (s != "") {
                    String client[] = s.split(" ");
                    Client newClient = new Client(client[0], client[1]);
                    roomList.get(i).roomHistory.add(newClient);
                }
            }
        }
    }
    public static void readFromStoredData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("storedData.txt"));
            StringBuilder builder = new StringBuilder();
            String singleLine = reader.readLine();
            for (int i = 0; i < 10; i++) {
                builder.append(singleLine);
                builder.append(System.lineSeparator());
                singleLine = reader.readLine();
            }
            String content = builder.toString();
            Hotel.generateRooms();
            loadToMemory(content);
            spreadStoredData();
        } catch (Exception ex) {
            System.out.println("Unable to read from storage...");
            consolePause();
        }
        System.out.println();
    }
    private static void loadToMemory(String content) {
        String[] data = content.split(System.getProperty("line.separator"));
        for (int i = 0; i < data.length; i+=2) {
            if (data[i].length() > 2) {
                dataClientList.add(data[i]);
            } else {
                dataClientList.add("");
            }
        }
        for (int i = 1; i < data.length; i+=2) {
            dataRoomStatus.add(Byte.parseByte(data[i]));
        }
    }

    public static void writeToStoredData() {
        System.out.println("Saving...");
        try {
            PrintWriter writer = new PrintWriter("storedData.txt", "UTF-8");
            List<Room> hotelRoomList = Hotel.getHotelRoomList();
            hotelRoomList.forEach( room -> {
                if (room.roomHistory.size() == 0) {
                    writer.println("");
                } else {
                    room.roomHistory.forEach(client -> {
                        writer.println(client.firstName +" "+ client.lastName+";");
                    });
                }
                writer.println(room.getRoomStatus());
            });
            writer.close();
            System.out.println("Data saved...");
        } catch (Exception ex) {
            System.out.println("Unable to save data...");
            consolePause();
        }
    }
    public static void consolePause() {
        Scanner s = new Scanner(System.in);
        System.out.println("Press enter to continue.....");
        s.nextLine();
        Menu.menu();
    }
}
