package com.example.flights.application.assignment.services.impl;

import com.example.flights.application.assignment.models.requests.TicketRequest;
import com.example.flights.application.assignment.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketServiceImplTest {

    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        this.ticketService = new TicketServiceImpl();
    }

    @Test
    void isTicketAvailable_shouldReturnFalse_whenTicketNotFound() {
        final TicketRequest request = new TicketRequest(1, 2, "test2");
        this.ticketService.isTicketAvailable(request.id());

        final Boolean result = this.ticketService.isTicketAvailable(request.id());

        assertEquals(false, result);
    }

    @Test
    void checkin_shouldReturnTrue_whenTicketWasUpdated() {
        final TicketRequest request = new TicketRequest(10, 2, "test2");

        final Boolean result = this.ticketService.checkin(request.id(), request);

        assertEquals(true, result);
    }

    @Test
    void checkin_shouldReturnFalse_whenTicketNotAvailable() {
        final TicketRequest request = new TicketRequest(1, 2, "test2");

        final Boolean result = this.ticketService.checkin(request.id(), request);

        assertEquals(false, result);
    }
}
