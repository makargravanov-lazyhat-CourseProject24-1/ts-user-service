package ru.jetlabs.ts.userservice.models

import java.time.LocalDateTime

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val email: String,
    val password: String,
    val emailVerified: Boolean,
    val passportSeries: String?,
    val passportNumber: String?,
    val phone: String?,
    val phoneVerified: Boolean,
    val createdAt: LocalDateTime,
)