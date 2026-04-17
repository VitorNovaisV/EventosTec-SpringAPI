package com.eventostec.api.controller;

import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//Annotations to mark this class as a controller
//"RequestMapping sets the subdomain used"

@RestController
@RequestMapping("/api/event")
public class EventController {


    //Auto importing the Service using the "Autowired" annotation

    @Autowired
    private EventService eventService;

    //The next method creates a new event.

    //Defining a POST type of request using the same subdomain defined earlier
    //it consumes a "multipart/form-data" to be able to receive files, such as an image

    //In the following these parameters are mapped such as the types needed.

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@RequestParam("title") String title,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("date") Long date,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {

        //Creating an Event object with the data acquired, by using a method call from Services passing an DTO to be filled
        Event newEvent = this.eventService.createEvent(new EventRequestDTO(title, description, date, city, state, remote, eventUrl, image));

        //Returns an http code + the contents of the previous method call.
        return ResponseEntity.ok(newEvent);
    }

    //The next method returns the details of an event and his coupons linked to it

    //Setting the subdomain to be used for this method + defining it as a GET request
    //In this case the subdomain is set to be the eventId, identified by the brackets

    //In the following the parameter of eventId is requested of the type UUID, by the url .

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable("eventId") UUID eventId) {

        //Calls the method from the Service
        EventDetailsDTO eventDetails = eventService.getEventDetails(eventId);

        //Returns an http code + the contents of the previous method call.
        return ResponseEntity.ok(eventDetails);
    }

    //The next method returns all the events in a pageable request

    //Defining a GET type of request using the same subdomain defined earlier
    //In this case the parameters are passed by the url, and a default value is set if omitted.

    //The response is going to be pageable, and the parameters are the page requested, and how many items per page are gonna be show.

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {

        //Calls the method from the Service
        List<EventResponseDTO> allEvents = this.eventService.getUpcomingEvents(page, pageSize);

        //Returns an http code + the contents of the previous method call.
        //if the content is empty returns the code of "not found"
        return allEvents.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(allEvents);
    }

    //The next method returns the events in a pageable request with a filter sent by url

    //Setting the subdomain to be used for this method + defining it as a GET request
    //In the following these parameters are mapped such as the types needed.

    @GetMapping("/filter")
    public ResponseEntity<List<EventResponseDTO>> filterEvents(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int pageSize,
                                                               @RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String city,
                                                               @RequestParam(required = false) String uf,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        //Calls the method from the Service
        List<EventResponseDTO> events = eventService.getFilteredEvents(page,pageSize,title,city,uf,startDate,endDate);

        //Returns an http code + the contents of the previous method call.
        return ResponseEntity.ok(events);
    }
}
