package com.revature.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;


@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int event_id;

    @NotNull
    @Column(nullable = false)
    private int user_id;

    @NotNull
    @Column(nullable = false)
    private String event_url;

    @Column(nullable = false)
    private Date event_date;

    @Column(nullable = false)
    private String event_title;

    public Event() {

    }

    public Event(int event_id, int user_id, String event_url, Date event_date, String event_title, String event_description) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.event_url = event_url;
        this.event_date = event_date;
        this.event_title = event_title;
        this.event_description = event_description;
    }

    @Column
    private String event_description;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEvent_url() {
        return event_url;
    }

    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }

    public Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }
}
