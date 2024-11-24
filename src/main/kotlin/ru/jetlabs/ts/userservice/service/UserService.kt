package ru.jetlabs.ts.userservice.service

import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.jetlabs.ts.userservice.models.UserCreateForm
import ru.jetlabs.ts.userservice.models.UserResponseForm
import ru.jetlabs.ts.userservice.models.UserUpdateForm
import ru.jetlabs.ts.userservice.models.UserUpdatePasswordForm
import ru.jetlabs.ts.userservice.tables.Users

@Component
@Transactional
class UserService {

    fun getById(id: Long): UserResponseForm? = Users.selectAll().where { Users.id eq id }.singleOrNull()?.let {
        UserResponseForm(
            id = it[Users.id].value,
            firstName = it[Users.firstName],
            lastName = it[Users.lastName],
            middleName = it[Users.middleName],
            email = it[Users.email],
            emailVerified = it[Users.emailVerified],
            passportSeries = it[Users.passportSeries],
            passportNumber = it[Users.passportNumber],
            phone = it[Users.phone],
            phoneVerified = it[Users.phoneVerified],
            createdAt = it[Users.createdAt]
        )
    }

    fun create(form: UserCreateForm): Long = Users.insertAndGetId {
        it[firstName] = form.firstName
        it[lastName] = form.lastName
        it[email] = form.email
        it[password] = form.password
    }.value

    fun updatePassword(form: UserUpdatePasswordForm): Boolean {
        val currentPassword =
            Users.select(Users.password).where { Users.id eq form.id }.singleOrNull()?.get(Users.password)

        return if (currentPassword == form.previousPassword) {
            Users.update(where = { Users.id eq form.id }) { it[password] = form.newPassword } == 1
        } else false
    }

    fun update(form: UserUpdateForm): Int = Users.update {
        it[firstName] = form.firstName
        it[lastName] = form.lastName
        it[middleName] = form.middleName
        it[email] = form.email
        it[emailVerified] = form.emailVerified
        it[passportSeries] = form.passportSeries
        it[passportNumber] = form.passportNumber
        it[phone] = form.phone
        it[phoneVerified] = form.phoneVerified
    }
}

