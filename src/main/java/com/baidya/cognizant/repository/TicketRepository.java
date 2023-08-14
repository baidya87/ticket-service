package com.baidya.cognizant.repository;

import com.baidya.cognizant.pojo.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
