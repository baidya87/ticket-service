package com.baidya.cognizant.resource;

import com.baidya.cognizant.pojo.Ticket;
import com.baidya.cognizant.service.TicketService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

}
