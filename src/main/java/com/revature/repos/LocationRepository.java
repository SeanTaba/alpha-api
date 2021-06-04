package com.revature.repos;


import com.revature.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer>
{
    Location getLocationById(@Param("location_id") int lid);
    List<Location> getLocationByCity(@Param("city") String cn);
}
