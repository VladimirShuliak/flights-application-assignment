package com.example.flights.application.assignment.controllers;

import com.example.flights.application.assignment.models.requests.TicketRequest;
import com.example.flights.application.assignment.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;

    public TicketController(final TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> isTicketAvailable(final @PathVariable Integer ticketId) {
        LOG.info("Request to check ticket");

        final Boolean isAvailable = this.ticketService.isTicketAvailable(ticketId);

        return ResponseEntity.ok(isAvailable);
    }

    @PutMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> checkin(
        @PathVariable Integer ticketId,
        @RequestBody @Valid final TicketRequest requests) {
        LOG.info("Request to update ticket");

        final Boolean checkin = this.ticketService.checkin(ticketId, requests);


        return ResponseEntity.ok(checkin);
    }
}
