package com.example.calendarapp;

import java.time.LocalDate;
import java.util.Date;

public class Event {
    private String eventTitle;
    private String eventDescription;
    private LocalDate eventDate;

    public Event (String title, String description, LocalDate date) {
        eventTitle = title;
        eventDescription = description;
        eventDate = date;
    }

    public void setTitle (String title) {
        eventTitle = title;
    };

    public void setDescription (String description) {
        eventDescription = description;
    }

    public void setDate (LocalDate date) {
        eventDate = date;
    }

    public String getEventTitle () {
        return eventTitle;
    }

    public String getEventDescription () {
        return  eventDescription;
    }

    public LocalDate getDate () {
        return eventDate;
    }
}
