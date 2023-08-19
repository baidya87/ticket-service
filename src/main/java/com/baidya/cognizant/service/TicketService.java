package com.baidya.cognizant.service;

import com.baidya.cognizant.pojo.Price;
import com.baidya.cognizant.pojo.Ticket;
import com.baidya.cognizant.repository.TicketRepository;
import com.baidya.cognizant.resource.TicketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    private RestTemplate restTemplate;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public CollectionModel<EntityModel<Ticket>> fetchAll(){
        List<Ticket> tickets = ticketRepository.findAll();
        List<EntityModel<Ticket>> entityModels = tickets.stream().map(ticket -> EntityModel.of(ticket, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).get(ticket.getId())).withSelfRel())).collect(Collectors.toList());
        return CollectionModel.of(entityModels, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).get()).withSelfRel());
    }

    public EntityModel<Ticket> findOne(long id) {
       Optional<Ticket> ticktOptional = ticketRepository.findById(id);
       if(ticktOptional.isPresent()){
           return EntityModel.of(ticktOptional.get(),
                   WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).get(ticktOptional.get().getId())).withSelfRel());
       }else{
           throw new RuntimeException("Ticket NOT FOUND.");
       }
    }

    public float getPrice(int count, String event) {
       String url =  "http://localhost:9090/prices/"+event;
       ResponseEntity<Price> responseEntity = restTemplate.getForEntity(url, Price.class);
       Price price = responseEntity.getBody();
       float totalTicketPrice = price.getPrice()* count;
       float amount = (totalTicketPrice + (totalTicketPrice * price.getTax() / 100.00f)) ;
       return amount;
    }
}
