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

    @GetMapping("/overview/{id}")
    fun getTicketInfo(@PathVariable id: UUID): ResponseEntity<Ticket> {
        val ticket = ticketService.getTicketInfo(id)
        return ResponseEntity.ok(ticket)
    }

    @GetMapping("/total", produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseBody
    fun getTotalTicketsHTML(): String {
        val totalTickets = ticketService.getTotalTicketsGenerated()
        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Total Tickets Generated</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    margin: 0;
                    background-color: #f0f0f0;
                }
                .container {
                    text-align: center;
                    background-color: white;
                    padding: 2rem;
                    border-radius: 10px;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                }
                h1 {
                    color: #333;
                }
                .total {
                    font-size: 3rem;
                    font-weight: bold;
                    color: #4CAF50;
                    margin: 1rem 0;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Total Tickets Generated</h1>
                <div class="total">${totalTickets}</div>
            </div>
        </body>
        </html>
        """.trimIndent()
    }
}

data class TicketRequest(val vatin: String, val firstName: String, val lastName: String)