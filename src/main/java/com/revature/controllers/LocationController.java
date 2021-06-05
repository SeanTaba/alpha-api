package com.revature.controllers;


import com.revature.models.Location;
import com.revature.repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
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

    @RequestMapping()
    public Set<String> listCountries()
    {
        try
        {
            return locationToString(locationRepository.findAll(), Location.class.getMethod("getCountry"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping("/{country}")
    public Set<String> getCountries(@PathVariable String country)
    {
        try
        {
            return locationToString(locationRepository.findByCountry(country), Location.class.getMethod("getState"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping(path = "/{country}/{state}")
    public Set<String> getCounties(@PathVariable String country, @PathVariable String state)
    {
        try
        {
            return locationToString(locationRepository.findByCountryAndState(country,state), Location.class.getMethod("getCounty"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping(path = "/{country}/{state}/{county}")
    public Set<String> getCities(@PathVariable String country, @PathVariable String state, @PathVariable String county)
    {
        try
        {
            return locationToString(locationRepository.findByCountryAndStateAndCounty(country,state,county), Location.class.getMethod("getCity"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping(path = "/{country}/{state}/{county}/{city}")
    public Set<String> getCoordinates(@PathVariable String country, @PathVariable String state, @PathVariable String county, @PathVariable String city)
    {
        try
        {
            return locationToString(locationRepository.findByCountryAndStateAndCountyAndCity(country,state,county,city), Location.class.getMethod(
                                                                                                                    "getCoordinatesString"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    private Set<String> locationToString(Collection<Location> locations, Method method)
    {
        Set<String> strings = new HashSet<>();
        locations.forEach(location ->
                          {
                              try
                              {
                                  strings.add((String) method.invoke(location));
                              } catch (IllegalAccessException | InvocationTargetException e)
                              {
                                  e.printStackTrace();
                              }
                          });
        return strings;
    }
}
