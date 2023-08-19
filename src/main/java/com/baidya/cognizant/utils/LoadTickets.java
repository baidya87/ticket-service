package com.baidya.cognizant.utils;

import com.baidya.cognizant.pojo.Ticket;
import com.baidya.cognizant.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class LoadTickets {

    Logger LOGGER = LoggerFactory.getLogger(LoadTickets.class);
    private final TicketRepository ticketRepository;

    public LoadTickets(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Bean
    public CommandLineRunner loadEventTickets(){
        return (args) -> {
            LOGGER.info(String.format("Loading ticket -> %s ", ticketRepository.save(new Ticket(0L, "KJKK8876", "Spiderman 3", 2, 20f))));
        };
    }
}
