package ru.jetlabs.ts.userservice.models

data class UserCreateForm(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)