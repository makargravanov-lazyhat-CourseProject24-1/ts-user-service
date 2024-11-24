package ru.jetlabs.ts.userservice.models

import java.time.LocalDateTime

data class UserResponseForm(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val email: String,
    val emailVerified: Boolean,
    val passportSeries: String?,
    val passportNumber: String?,
    val phone: String?,
    val phoneVerified: Boolean,
    val createdAt: LocalDateTime,
)

fun User.toUserResponse(): UserResponseForm = UserResponseForm(
    id = id,
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    email = email,
    emailVerified = emailVerified,
    passportSeries = passportSeries,
    passportNumber = passportNumber,
    phone = phone,
    phoneVerified = phoneVerified,
    createdAt = createdAt
)