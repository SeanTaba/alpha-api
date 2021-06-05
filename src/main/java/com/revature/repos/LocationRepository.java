package com.revature.repos;

import com.revature.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer>
{
    Location findById(int lid);
    List<Location> findByCity(String cn);
    @Query(value = "select state from locations",nativeQuery = true)
    Set<String> findAllStates();
    @Query(value = "select county from locations l where l.state = ?1",nativeQuery = true)
    Set<String> findAllCountiesByState(String state);
    @Query(value = "select city from locations l where l.state = ?1 and l.county = ?2",nativeQuery = true)
    Set<String> findAllCitiesByStateAndCounty(String state, String county);
    @Query(value = "select lat, lng from locations l where l.state = ?1 and l.county = ?2 and l.city = ?3", nativeQuery = true)
    Map<Double, Double> findCoordinates(String state, String county, String city);
}
