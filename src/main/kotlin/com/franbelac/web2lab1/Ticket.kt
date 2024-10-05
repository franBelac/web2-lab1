package com.franbelac.web2lab1

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Ticket(
    @Id @GeneratedValue val id: UUID = UUID.randomUUID(),
    val vatin: String,
    val firstName: String,
    val lastName: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
