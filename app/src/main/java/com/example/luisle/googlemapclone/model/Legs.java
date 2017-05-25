package com.example.luisle.googlemapclone.model;

/**
 * Created by LuisLe on 5/25/2017.
 */

public class Legs {
    private Distance distance;
    private Duration duration;
    private String end_address;
    private String start_address;
    private Location end_location;
    private Location start_location;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }

    public Legs(Distance distance, Duration duration, String end_address, String start_address, Location end_location, Location start_location) {
        this.distance = distance;
        this.duration = duration;
        this.end_address = end_address;
        this.start_address = start_address;
        this.end_location = end_location;
        this.start_location = start_location;
    }
}
