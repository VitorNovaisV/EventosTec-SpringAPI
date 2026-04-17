package com.eventostec.api.domain.event;

import java.util.Date;
import java.util.UUID;

//DTO used for receiving the data of Events

public record EventResponseDTO(UUID id, String title, String description, Date date, String city, String state, Boolean remote, String eventUrl, String imageUrl) {
}

