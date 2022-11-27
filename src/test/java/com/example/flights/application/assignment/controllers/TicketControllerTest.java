package com.example.flights.application.assignment.controllers;

import com.example.flights.application.assignment.models.requests.TicketRequest;
import com.example.flights.application.assignment.services.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
@ContextConfiguration
class TicketControllerTest {

    private static final String URI = "/tickets";

    @MockBean
    private TicketService ticketService;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        this.objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void isTicketAvailable() throws Exception {
        when(this.ticketService.checkin((anyInt()), any(TicketRequest.class))).thenReturn(true);
        RequestBuilder builder = get(URI + "/" + 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(builder)
            .andExpect(status().isOk())
            .andDo(print());

        verify(this.ticketService, times(1)).isTicketAvailable(any());
    }

    @Test
    void checkin() throws Exception {
        final TicketRequest request = new TicketRequest(1, 1, "test");
        final String requestString = this.objectMapper.writeValueAsString(request);
        when(this.ticketService.checkin((anyInt()), any(TicketRequest.class))).thenReturn(true);
        RequestBuilder builder = put(URI + "/" + 1)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestString);

        this.mockMvc.perform(builder)
            .andExpect(status().isOk())
            .andDo(print());

        verify(this.ticketService, times(1)).checkin(any(), any());
    }
}
