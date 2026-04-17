package com.eventostec.api.service;


import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    //Auto importing the Service using the "Autowired" annotation

    @Autowired
    private AddressRepository addressRepository;

    //method that creates a new address using the data passed by the creation of a new event,
    //and the event that is linked to it, creating an address object and saving it on the repository

    public void createAddress(EventRequestDTO data, Event event) {

        Address address = new Address();
        //creating a new address object

        address.setCity(data.city());
        address.setUf(data.state());
        address.setEvent(event);
        //setting the attributes

        //saving in the repository
        addressRepository.save(address);
    }
}
