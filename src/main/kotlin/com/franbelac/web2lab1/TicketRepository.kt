package com.franbelac.web2lab1

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TicketRepository : JpaRepository<Ticket, UUID> {
    fun countByVatin(vatin: String): Int
}