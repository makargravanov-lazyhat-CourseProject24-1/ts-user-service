package ru.jetlabs.ts.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Users:  LongIdTable(name = "users") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val middleName = varchar("middle_name", 50).nullable().clientDefault { null }
    val email = varchar("email", 50).uniqueIndex().check { it regexp "^\\w+@\\w+\\.\\w{2,3}$" }
    val password = varchar("password", 50)
    val emailVerified = bool("email_verified").clientDefault { false }
    val passportSeries = char("passport_series", 4).nullable().clientDefault { null }.uniqueIndex("passport_series")
    val passportNumber = char("passport_number", 6).nullable().clientDefault { null }.uniqueIndex("passport_number")
    val phone = char("phone", 10).nullable().clientDefault { null }
    val phoneVerified = bool("phone_verified").clientDefault { false }
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
}