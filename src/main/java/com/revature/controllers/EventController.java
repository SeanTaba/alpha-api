package com.revature.controllers;


import com.revature.models.Event;
import com.revature.models.User;
import com.revature.repos.EventRepository;
import com.revature.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/")
    public String test()
    {
        return "";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllEvents()
    {
        return eventRepository.findAll();
    }


}
