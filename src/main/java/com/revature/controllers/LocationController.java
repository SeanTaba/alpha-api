package com.revature.controllers;


import com.revature.models.Location;
import com.revature.repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController
{
    private LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    @RequestMapping("/getLocationById")
    public Location getLocationById(@RequestParam("lid") int lid)
    {
        return locationRepository.getLocationById(lid);
    }

    @RequestMapping("/getLocationByCity")
    public List<Location> getLocationByCity(@RequestParam("lc") String lc)
    {
        return locationRepository.getLocationByCity(lc);
    }
}
