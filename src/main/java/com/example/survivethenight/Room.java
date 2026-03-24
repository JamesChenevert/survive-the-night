package com.example.survivethenight;

import java.util.*;

public class Room {

    public String name;
    public String description;
    public String imageLayout;

    public Map<String, Room> exits;
    public List<String> items;

    public Room(String name, String description, String imageLayout) {
        this.name = name;
        this.description = description;
        this.imageLayout = imageLayout;

        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }
}