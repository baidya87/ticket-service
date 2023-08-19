package com.baidya.cognizant.resource;

import com.baidya.cognizant.pojo.Ticket;
import com.baidya.cognizant.service.TicketService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ticket>> get(@PathVariable("id") long id){
        EntityModel<Ticket> entityModel = ticketService.findOne(id);
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping
    public ResponseEntity<Object> get(){
        CollectionModel<EntityModel<Ticket>> collectionModel = ticketService.fetchAll();
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/price/{count}/{event}")
    public ResponseEntity<EntityModel<Ticket>> getPrice(@PathVariable("count") int count, @PathVariable("event") String event){
        float price = ticketService.getPrice(count, event);
        Ticket ticket = new Ticket((int)Math.random()*10, "socc-10-JJUJ", event, count, price);

        return ResponseEntity.ok(EntityModel.of(ticket, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).getPrice(count, event)).withSelfRel()));
    }

}
