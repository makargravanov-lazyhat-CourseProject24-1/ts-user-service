package ru.jetlabs.ts.userservice.service

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.jetlabs.ts.userservice.models.UserCreateForm
import ru.jetlabs.ts.userservice.models.UserResponseForm
import ru.jetlabs.ts.userservice.models.UserUpdateForm
import ru.jetlabs.ts.userservice.models.UserUpdatePasswordForm
import ru.jetlabs.ts.userservice.rest.UserFindForm
import ru.jetlabs.ts.userservice.tables.Users

@Component
@Transactional
class UserService {

    fun findByEmailAndPassword(form: UserFindForm): UserResponseForm? =
        Users.selectAll().where { Users.email eq form.email }.singleOrNull()?.takeIf {
            BCrypt.checkpw(form.password, it[Users.password])
        }?.mapToUserResponseForm()

    fun getById(id: Long): UserResponseForm? =
        Users.selectAll().where { Users.id eq id }.singleOrNull()?.mapToUserResponseForm()

    fun create(form: UserCreateForm): Long = Users.insertAndGetId {
        it[firstName] = form.firstName
        it[lastName] = form.lastName
        it[email] = form.email
        it[password] = BCrypt.hashpw(form.password, BCrypt.gensalt())
    }.value

    fun updatePassword(id: Long, form: UserUpdatePasswordForm): Boolean {
        val currentPassword =
            Users.select(Users.password).where { Users.id eq id }.singleOrNull()?.get(Users.password)

        return if (BCrypt.checkpw(form.previousPassword, currentPassword)) {
            Users.update(where = { Users.id eq id }) {
                it[password] = BCrypt.hashpw(form.newPassword, BCrypt.gensalt())
            } == 1
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

    fun ResultRow.mapToUserResponseForm(): UserResponseForm =
        UserResponseForm(
            id = this[Users.id].value,
            firstName = this[Users.firstName],
            lastName = this[Users.lastName],
            middleName = this[Users.middleName],
            email = this[Users.email],
            emailVerified = this[Users.emailVerified],
            passportSeries = this[Users.passportSeries],
            passportNumber = this[Users.passportNumber],
            phone = this[Users.phone],
            phoneVerified = this[Users.phoneVerified],
            createdAt = this[Users.createdAt]
        )
}

