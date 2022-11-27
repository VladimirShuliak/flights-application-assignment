package com.example.flights.application.assignment.services.impl;

import com.example.flights.application.assignment.models.Ticket;
import com.example.flights.application.assignment.models.requests.TicketRequest;
import com.example.flights.application.assignment.services.LocalInMemoryCache;
import com.example.flights.application.assignment.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private static final int CACHE_TIME_TO_LIVE = 100;
    private static final int CACHE_TIMER_INTERVAL = 100;
    private static final int CACHE_MAX_ITEMS = 2;

    private final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    private static final List<Ticket> STORED_TICKETS = List.of(
        Ticket.newBuilder().id(10).checkinStatus(false).build(),
        Ticket.newBuilder().id(11).checkinStatus(false).build(),
        Ticket.newBuilder().id(12).checkinStatus(false).build(),
        Ticket.newBuilder().id(13).checkinStatus(false).build());

    private final LocalInMemoryCache<Integer, Ticket> cache = new LocalInMemoryCache<>(
        CACHE_TIME_TO_LIVE,
        CACHE_TIMER_INTERVAL,
        CACHE_MAX_ITEMS);


    @Override
    public Boolean isTicketAvailable(final Integer ticketId) {
        log.info("Request to check ticket");

        if (cache.get(ticketId) != null) {
            return Boolean.TRUE;
        }
        final Map<Integer, Ticket> availableTickets = STORED_TICKETS.stream()
            .filter(t -> Boolean.FALSE.equals(t.getCheckinStatus()))
            .collect(Collectors.toMap(Ticket::getId, Function.identity()));

        cache.put(ticketId, availableTickets.get(ticketId));

        return availableTickets.containsKey(ticketId);
    }

    @Override
    public Boolean checkin(final Integer ticketId, final TicketRequest request) {
        log.info("Request for check-in");
        if (this.isTicketAvailable(ticketId)) {
            final Ticket availableTicket = STORED_TICKETS.stream()
                .filter(t -> ticketId.equals(t.getId()))
                .findFirst()
                .get();

            cache.put(availableTicket.getId(), availableTicket);

            availableTicket.setDestinationId(request.destinationId());
            availableTicket.setBaggageId(request.baggageId());
            availableTicket.setCheckinStatus(Boolean.TRUE);

            log.info("Ticket updated: {}", availableTicket);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
