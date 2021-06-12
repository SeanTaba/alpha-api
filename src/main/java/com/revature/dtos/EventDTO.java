package com.revature.dtos;

import java.sql.Date;

public class EventDTO {

    private  int eventId;
    private  int userId;
    private  String eventUrl;
    private  Date eventDate;
    private  String eventTitle;
    private  String eventDescription;

    public EventDTO(){
        super();
    }

    public EventDTO(int eventId, int userId, String eventUrl, Date eventDate, String eventTitle, String eventDescription) {
        this.eventId = eventId;
        this.userId = userId;
        this.eventUrl = eventUrl;
        this.eventDate = eventDate;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
