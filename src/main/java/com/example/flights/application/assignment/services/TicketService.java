package com.example.flights.application.assignment.services;

import com.example.flights.application.assignment.models.requests.TicketRequest;

public interface TicketService {

    Boolean isTicketAvailable(Integer ticketId);

    Boolean checkin(Integer ticketId, TicketRequest request);
}
