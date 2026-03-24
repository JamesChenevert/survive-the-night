package com.example.survivethenight;

import com.example.survivethenight.JDBCDao;
import com.example.survivethenight.Room;

import java.util.*;

public class GameController {

    private Scanner scanner = new Scanner(System.in);

    private List<String> inventory = new ArrayList<>();

    private Room currentRoom;

    private Room loadingDock;
    private Room Office;
    private Room kitchenHall;
    private Room partsAndService;

    private JDBCDao dao = new JDBCDao();

    public void startGame() {
        createWorld();

        // LOAD saved inventory from DB
        inventory = dao.loadInventory();

        System.out.println("WELCOME TO SURVIVE THE NIGHT");
        System.out.println("Do you want to start the game? (yes/no)");

        String input = scanner.nextLine().toLowerCase();

        if (input.equals("yes")) {
            createWorld();

            // LOAD saved inventory from DB
            inventory = dao.loadInventory();

            System.out.println(" Starting game...");
            gameLoop();

        } else if (input.equals("no")) {
            System.out.println("Goodbye!");
            System.exit(0);

        } else {
            System.out.println("Please type 'yes' or 'no'. ");
                    startGame(); // ask again
        }
    }

    private void createWorld() {

        loadingDock = new Room("Loading dock",
                "You enter the loading dock, you see containers, crates and boxes with the logo Fazbear Entertainment on them.",
                "");

        Office = new Room("Office",
                "You walk back into your office, everything is as normal from when you left it.",
                "");

        kitchenHall = new Room("Kitchen hall",
                "You walk into the kitchen hall, you see papers on the walls, posters and 2-3 shelves.",
                "");

        partsAndService = new Room("Parts & Service",
                "You walk into parts & service and see an animatronic on a table powered off, looks like its being worked on.",
                "");

        // CONNECT ROOMS
        partsAndService.exits.put("north", Office);
        partsAndService.exits.put("east", kitchenHall);

        Office.exits.put("loading Dock", partsAndService);
        kitchenHall.exits.put("parts & service", partsAndService);

        // ITEMS
        loadingDock.items.add("Axe");
        // .items.add("");

        currentRoom = loadingDock;
    }

    private void gameLoop() {
        while (true) {

            showRoom();

            System.out.println("\nCommands:");
            System.out.println("go [direction]");
            System.out.println("take [item]");
            System.out.println("use [item]");
            System.out.println("inventory");
            System.out.println("quit");

            String input = scanner.nextLine().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            else if (input.startsWith("go ")) {
                move(input.substring(3));
            }
            else if (input.startsWith("take ")) {
                takeItem(input.substring(5));
            }
            else if (input.startsWith("use ")) {
                useItem(input.substring(4));
            }
            else if (input.equals("inventory")) {
                showInventory();
            }
            else {
                System.out.println("Invalid command.");
            }
        }
    }

    private void showRoom() {
        System.out.println("\n== " + currentRoom.name + " ==");
        System.out.println(currentRoom.description);

        GameApplication.updateImage(currentRoom.imageLayout);

        if (!currentRoom.items.isEmpty()) {
            System.out.println("Items here: " + currentRoom.items);
        }

        System.out.println("Exits: " + currentRoom.exits.keySet());
    }

    private void move(String direction) {
        if (currentRoom.exits.containsKey(direction)) {
            currentRoom = currentRoom.exits.get(direction);
        } else {
            System.out.println("You can't go that way.");
        }
    }

    private void takeItem(String item) {

        item = capitalize(item);

        if (currentRoom.items.contains(item)) {
            inventory.add(item);
            currentRoom.items.remove(item);

            // SAVE to database
            dao.saveItem(item);

            System.out.println("Picked up: " + item);
        } else {
            System.out.println("Item not found.");
        }
    }

    // USE ITEM ANYWHERE
    private void useItem(String item) {

        item = capitalize(item);

        if (inventory.contains(item)) {
            System.out.println("You used: " + item);

            // Example usage effect
            if (item.equals("Axe")) {
                System.out.println("You used the Axe on the burning door.");
            }
//            else if (item.equals("")) {
//                System.out.println("");
//            }

        } else {
            System.out.println("You don't have that item.");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Inventory: " + inventory);
        }
    }


    private String capitalize(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
}