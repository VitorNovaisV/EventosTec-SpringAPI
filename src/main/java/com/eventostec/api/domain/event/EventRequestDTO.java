package com.eventostec.api.domain.event;

import org.springframework.web.multipart.MultipartFile;

//DTO used for sending the data of Events

public record EventRequestDTO(String title, String description, Long date, String city, String state, Boolean remote, String eventUrl, MultipartFile image) {
}
