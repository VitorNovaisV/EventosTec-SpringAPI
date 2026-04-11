package com.eventostec.api.service;

import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Date;
import java.util.UUID;

@Service
public class EventService {

    @Value("${supabase.s3.bucket-name}")
    private String bucketName;

    @Value("${supabase.s3.supabaseUrl}")
    private String supabaseUrl;

    @Autowired
    private S3Client s3Client;


    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventRequestDTO data) {



        String imgUrl = this.uploadImg(data.image());

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(imgUrl);
        newEvent.setRemote(data.remote());

        eventRepository.save(newEvent);

        return newEvent;
    }

    public String uploadImg(MultipartFile file){

        try {
            String fileName =  UUID.randomUUID().toString()+"."+file.getOriginalFilename();

            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putOb, RequestBody.fromBytes(file.getBytes()));

            return String.format("%s/storage/v1/object/public/%s/%s",
                    supabaseUrl, bucketName, fileName);

        }catch (Exception e){
            System.out.println("Erro ao enviar Imagem" + e);
            return null ;
        }
    }

}
