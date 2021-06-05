package com.revature.repos;

import com.revature.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer>
{
    List<Location> findAll();
    Set<Location> findByCountry(String country);
    Set<Location> findByCountryAndState(String country, String state);
    Set<Location> findByCountryAndStateAndCounty(String country, String state, String county);
    Set<Location> findByCountryAndStateAndCountyAndCity(String country, String state, String county, String city);
}
