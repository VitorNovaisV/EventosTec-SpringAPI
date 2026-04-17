package com.eventostec.api.service;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Value("${supabase.s3.bucket-name}")
    private String bucketName;

    @Value("${supabase.s3.supabaseUrl}")
    private String supabaseUrl;

    //importing values from .env equivalent of spring.

    @Autowired
    private S3Client s3Client;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private EventRepository eventRepository;

    //Auto importing the Service using the "Autowired" annotation


    //Method that creates a new event
    public Event createEvent(EventRequestDTO data) {

        //receives the imgUrl as a return from the method call, responsible for
        //storing the image

        String imgUrl = this.uploadImg(data.image());

        Event newEvent = new Event();
        //Creating a new Event object

        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(data.remote());
        //Setting attributes

        eventRepository.save(newEvent);
        //Saving it on the repository

        if (!data.remote()) {

            //method call to the Address Service,
            //creating a new address linked to the newly created event
            this.addressService.createAddress(data,newEvent);
        }
        //if the previous object isn't remote, creates a new address entity and saves it

        return newEvent;
        //returns the object to the controller
    }

    //method that saves the image file
    public String uploadImg(MultipartFile file){

        try {
            //defines the file name using a random Generated UUID, + the original name submitted
            String fileName =  UUID.randomUUID() + "."+file.getOriginalFilename();

            //creates the object that can be sent by the S3,
            //using as parameters the file,
            //the name of the storage("bucketName")
            //and the file type
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            //Sends it to the storage
            s3Client.putObject(putOb, RequestBody.fromBytes(file.getBytes()));

            //after successfully sending it, return the formated url to use
            return String.format("%s.supabase.co/storage/v1/object/public/%s/%s",
                    supabaseUrl, bucketName, fileName);

            //Error handling
        }catch (Exception e){
            System.out.println("Error while uploading image " + e);
            return null ;
        }
    }

    //method that gets events that haven't happened
    //it returns a pageable list, and the parameters are the page requested,
    //and how many items per page are gonna be show.
    public List<EventResponseDTO> getUpcomingEvents(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        //creating a new pageable object

        Page<Event> eventsPage = this.eventRepository.findUpcomingEvents(new Date(), pageable);
        //method call to the repository

        //creates a list of DOTs to return.
        //in the "city", and "uf" attributes, they can be empty if the event is remote.
        return eventsPage.map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() != null ? event.getAddress().getCity(): "",
                        event.getAddress() != null ? event.getAddress().getUf(): "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl()))
                .stream().toList();
    }

   //method that looks for the details of an event and his coupons linked to it, and then returns it

    public EventDetailsDTO getEventDetails(UUID eventId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not Found"));
        //Checks to see if desired event exists

        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());
        //Finds all the coupons linked to the desired event.


        List<EventDetailsDTO.CouponDTO> couponDTOS = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        //using a stream, runs through all coupons found related to the eventId,and adds them to the DTO as a list


        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getAddress() != null ? event.getAddress().getCity(): "",
                event.getAddress() != null ? event.getAddress().getUf(): "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl(),
                couponDTOS);

        //creating the DTO to be sent, adding the Coupons list at the end

    }

    //method that returns the events in a pageable request with a filter sent by url
    //it returns a pageable list, and the parameters are the page requested,
    //and how many items per page are gonna be show.
    //filters are also passed by parameters, all optional

    public List<EventResponseDTO> getFilteredEvents(int page, int size,String title, String city, String uf, Date startDate, Date endDate) {
        title = title == null ? "" : title;
        city = city == null ? "" : city;
        uf = uf == null ? "" : uf;
        startDate = startDate == null ? new Date(0) : startDate;
        endDate = endDate == null ? new Date() : endDate;


        Pageable pageable = PageRequest.of(page, size);
        //creating a new pageable object

        Page<Event> eventsPage = this.eventRepository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);
        //method call to the repository

        return eventsPage.map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getAddress() != null ? event.getAddress().getCity(): "",
                        event.getAddress() != null ? event.getAddress().getUf(): "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl()))
                .stream().toList();

        //creates a list of DOTs to return.
    }

}
