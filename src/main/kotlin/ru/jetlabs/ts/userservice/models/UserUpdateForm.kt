package ru.jetlabs.ts.userservice.models

data class UserUpdateForm(
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
)

data class UserUpdatePasswordForm(
    val previousPassword: String,
    val newPassword: String,
)

data class UserUpdatePassportForm(
    val id: Long,
    val passportSeries: String,
    val passportNumber: String,
)