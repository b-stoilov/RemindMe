package com.example.calendarapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;



    private String eventTitle;
    private String eventDescription;
    private String eventDate;
    private String eventHour;

    public Event(String eventTitle, String eventDescription, String eventDate, String eventHour) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventHour = eventHour;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventHour(String eventHour) {
        this.eventHour = eventHour;
    }

    public int getId() {
        return id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventHour () {
        return eventHour;
    }
}
