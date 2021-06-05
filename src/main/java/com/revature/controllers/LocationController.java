package com.revature.controllers;


import com.revature.models.Location;
import com.revature.repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/locations")
public class LocationController
{
    private final LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }
    @RequestMapping("/getLocationById")
    public Location getLocationById(@RequestParam("lid") int lid)
    {
        return locationRepository.findById(lid);
    }

    @RequestMapping("/getLocationByCity")
    public List<Location> getLocationByCity(@RequestParam("lc") String lc)
    {
        return locationRepository.findByCity(lc);
    }

    @RequestMapping("/states")
    public Set<String> getStates()
    {
        return locationRepository.findAllStates();
    }

    @RequestMapping(path = "/states/{state}")
    public Set<String> getCounties(@PathVariable String state)
    {
        return locationRepository.findAllCountiesByState(state);
    }

    @RequestMapping(path = "/states/{state}/{county}")
    public Set<String> getCities(@PathVariable String state, @PathVariable String county)
    {
        return locationRepository.findAllCitiesByStateAndCounty(state, county);
    }

    @RequestMapping(path = "/states/{state}/{county}/{city}")
    public Map<Double,Double> getCoordinates(@PathVariable String state, @PathVariable String county, @PathVariable String city)
    {
        return locationRepository.findCoordinates(state, county, city);
    }
}
