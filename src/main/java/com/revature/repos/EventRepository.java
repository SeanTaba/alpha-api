package com.revature.repos;


import com.revature.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Transactional
    Event findAllEvents(int user_id);
}
