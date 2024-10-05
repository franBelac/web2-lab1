package com.franbelac.web2lab1

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TicketService(private val ticketRepository: TicketRepository) {
    fun generateTicket(vatin: String, firstName: String, lastName: String): UUID {
        if (ticketRepository.countByVatin(vatin) >= 3) {
            throw IllegalStateException("Maximum number of tickets reached for this VATIN")
        }
        val ticket = Ticket(vatin = vatin, firstName = firstName, lastName = lastName)
        val savedTicket = ticketRepository.save(ticket)
        return savedTicket.id
    }

    fun getTicketInfo(id: UUID): Ticket {
        return ticketRepository.findById(id).orElseThrow { NoSuchElementException("Ticket not found") }
    }

    fun getTotalTicketsGenerated(): Long {
        return ticketRepository.count()
    }
}