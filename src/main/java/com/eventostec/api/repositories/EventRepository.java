package com.eventostec.api.repositories;

import com.eventostec.api.domain.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;


//The Repository is the representation of the connection with the database, it has similar SQL like query's
//here it needs to extend the JpaRepository that already uses the variables to connect defined in the .dev

public interface EventRepository  extends JpaRepository<Event, UUID> {

    //Using a @Query annotation to make a more specific type of query that supply my needs
    // it uses JPQL (Java Persistence Query Language). it's similar to SQL

    //the following function returns a Page of Events,
    //It's similar to a list but with pagination already implemented by spring
    //As parameters is asked the current date, and the Pageable class that allows the pagination to work

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >=currentDate")
    Page<Event> findUpcomingEvents(@Param("currentDate") Date currentDate, Pageable pageable);

    //Query a bit more complex than the last, using the concept of JOIN

    @Query("SELECT e.id AS id, e.title AS title, e.description AS description, e.date AS date, e.imgUrl AS imgUrl, e.eventUrl AS eventUrl, e.remote AS remote, a.city AS city, a.uf AS uf " +
            "FROM Event e JOIN Address a ON e.id = a.event.id " +
            "WHERE (:city = '' OR a.city LIKE %:city%) " +
            "AND (:uf = '' OR a.uf LIKE %:uf%) " +
            "AND (e.date >= :startDate AND e.date <= :endDate)")
    Page<Event> findFilteredEvents(@Param("title") String title,
                                   @Param("city") String city,
                                   @Param("uf") String uf,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   Pageable pageable);

}
