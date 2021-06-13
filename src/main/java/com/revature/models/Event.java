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
    @Column(nullable = false, name = "user_id")
    private int userId;

    @NotNull
    @Column(nullable = false)
    private String event_url;

    @Column(nullable = false)
    private Date event_date;

    @Column(nullable = false)
    private String event_title;


    public Event() {

    }

    public Event(int event_id, int user_id, String event_url, Date event_date, String event_title) {
        this.event_id = event_id;
        this.userId = user_id;
        this.event_url = event_url;
        this.event_date = event_date;
        this.event_title = event_title;
    }



    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
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


    @Override
    public String toString() {
        return "Event{" +
                "event_id=" + event_id +
                ", user_id=" + userId +
                ", event_url='" + event_url + '\'' +
                ", event_date=" + event_date +
                ", event_title='" + event_title + '\'' +
                '}';
    }
}
