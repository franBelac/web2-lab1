package com.franbelac.web2lab1

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO
import kotlin.NoSuchElementException

@Service
class TicketService(private val ticketRepository: TicketRepository) {
    fun generateTicket(vatin: String, firstName: String, lastName: String): Pair<String, ByteArray> {
        if (ticketRepository.countByVatin(vatin) >= 3) {
            throw IllegalStateException("Maximum number of tickets reached for this VATIN")
        }
        val ticket = Ticket(vatin = vatin, firstName = firstName, lastName = lastName)
        val savedTicket = ticketRepository.save(ticket)

        val fullUrl = "https://web2-lab1-backend.onrender.com/api/tickets/${savedTicket.id}"
        val qrCodeImage = generateQRCodeImage(fullUrl)

        return Pair(fullUrl, qrCodeImage)
    }

    fun generateQRCodeImage(text: String, width: Int = 300, height: Int = 300): ByteArray {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
        val bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix)

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }

    fun getTicketInfo(id: UUID): Ticket {
        return ticketRepository.findById(id).orElseThrow { NoSuchElementException("Ticket not found") }
    }

    fun getTotalTicketsGenerated(): Long {
        return ticketRepository.count()
    }
}