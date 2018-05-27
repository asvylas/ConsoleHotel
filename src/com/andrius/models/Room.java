package com.andrius.models;

import com.andrius.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public Room(byte newRoomNumber) {
        roomNumber = newRoomNumber;
    }
    private byte roomNumber;
    private byte roomStatus = 0;
    public List<Client> roomHistory = new ArrayList<>();

    public byte getRoomNumber() {
        return this.roomNumber;
    }
    public void setRoomStatus(byte roomStatus) {
        this.roomStatus = roomStatus;
    }
    public void checkinClient(String firstName, String lastName) {
        this.roomStatus = 1;
        Client newClient = new Client(firstName, lastName);
        roomHistory.add(newClient);
        System.out.println("Client: " + newClient.firstName +" "+ newClient.lastName+ " checked-in.");
        Util.writeToStoredData();
        Util.consolePause();
    }
    public void checkoutClient() {
        this.roomStatus = 0;
        Util.writeToStoredData();
    }
    public byte getRoomStatus(){
        return this.roomStatus;
    }
    public void getRoomInfo() {
        System.out.println("Clients stayed in this room:");
        if (this.roomHistory.size() > 0) {
            this.roomHistory.forEach(client -> {
                System.out.println(client.firstName + " " + client.lastName);
            });
        }else {
            System.out.println("None.");
        }
        String status = (this.roomStatus == 0 ) ? "free." : "taken.";
        System.out.println("Current status:");
        System.out.println(status);
        Util.consolePause();
    }
}
