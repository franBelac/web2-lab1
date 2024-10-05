package com.franbelac.web2lab1

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/tickets")
class TicketController(private val ticketService: TicketService) {
    @PostMapping
    fun generateTicket(@RequestBody ticketRequest: TicketRequest): ResponseEntity<ByteArray> {
        val (fullUrl, qrCodeImage) = ticketService.generateTicket(
            ticketRequest.vatin,
            ticketRequest.firstName,
            ticketRequest.lastName
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_PNG
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qr-code.png\"")
        headers.set("X-Ticket-URL", fullUrl)

        return ResponseEntity(qrCodeImage, headers, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTicketInfo(@PathVariable id: UUID): ResponseEntity<Ticket> {
        val ticket = ticketService.getTicketInfo(id)
        return ResponseEntity.ok(ticket)
    }

    @GetMapping("/total")
    fun getTotalTicketsGenerated(): ResponseEntity<Long> {
        val total = ticketService.getTotalTicketsGenerated()
        return ResponseEntity.ok(total)
    }
}

data class TicketRequest(val vatin: String, val firstName: String, val lastName: String)