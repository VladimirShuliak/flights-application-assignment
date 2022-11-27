package com.example.flights.application.assignment.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record TicketRequest(Integer id,
                            @NotNull Integer destinationId,
                            @NotEmpty String baggageId) {
}
