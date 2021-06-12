package com.revature.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.CityStateLocationDTO;
import com.revature.dtos.CoordinatesPair;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Event;
import com.revature.models.Location;
import com.revature.models.User;
import com.revature.repos.EventRepository;
import com.revature.repos.UserRepository;
import com.revature.security.JwtUtility;
import com.revature.services.EventAPIService;
import com.revature.services.LocationService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventRepository eventRepository;
    private EventAPIService eventAPIService;
    private LocationService locationService;
    private JwtUtility jwtUtility;
    private UserService userService;

   @Autowired
    public EventController(EventRepository eventRepository, EventAPIService eventAPIService, LocationService locationService, JwtUtility utility, UserService userService)
    {
        this.eventRepository = eventRepository;
        this.eventAPIService = eventAPIService;
        this.locationService = locationService;
        this.jwtUtility = utility;
        this.userService = userService;
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

    @RequestMapping("/hometown")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEventsAtHometown(HttpServletRequest req, HttpServletResponse res)throws IOException{
        String username = jwtUtility.getUserNameFromJwtToken((String) req.getAttribute("Authorize: "));
        ObjectMapper mapper = new ObjectMapper();
        try{
            User user = userService.getUserByUsername(username);
            CoordinatesPair<Double,Double> loc = locationService.getLatLonOfACity(user.getCity(),user.getState());
            String jsonRet = mapper.writeValueAsString(eventAPIService.getEvents(loc.getLatitude(),loc.getLongitude()));
            return ResponseEntity.accepted().body(jsonRet);
        }catch(InvalidRequestException | ParseException e){
            return ResponseEntity.badRequest().body(mapper.writeValueAsString(e));
        }
    }

    @RequestMapping("/location")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEventsAtLocation(@RequestBody CityStateLocationDTO locationDTO) throws IOException, ParseException {
        CoordinatesPair<Double,Double> loc = locationService.getLatLonOfACity(locationDTO.getCity(),locationDTO.getState());
        ObjectMapper mapper = new ObjectMapper();
        String jsonRet = mapper.writeValueAsString(eventAPIService.getEvents(loc.getLatitude(),loc.getLongitude()));
        return ResponseEntity.accepted().body(jsonRet);
    }

    @RequestMapping("/id")
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSpecificEvent(@RequestParam String eventId) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return ResponseEntity.accepted().body(mapper.writeValueAsString(eventAPIService.getEvent(eventId)));
    }

    @RequestMapping("/user")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUsersSavedEvents(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
        String username = jwtUtility.getUserNameFromJwtToken((String) req.getAttribute("Authorize: "));
        ObjectMapper mapper = new ObjectMapper();
        try{
            User user = userService.getUserByUsername(username);
            String jsonRet = mapper.writeValueAsString(eventRepository.getEventByUserId(user.getId()));
            return ResponseEntity.accepted().body(jsonRet);
        }catch(InvalidRequestException | JsonProcessingException e){
            return ResponseEntity.badRequest().body(mapper.writeValueAsString(e));
        }
    }


}
