package ru.jetlabs.ts.userservice.service

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.jetlabs.ts.userservice.UserServiceApplication
import ru.jetlabs.ts.userservice.models.*
import ru.jetlabs.ts.userservice.tables.Users
import java.sql.SQLException

@Component
@Transactional
class UserService {
    companion object {
        val LOGGER = LoggerFactory.getLogger(UserServiceApplication::class.java)!!
    }

    fun findByEmailAndPassword(form: UserFindForm): FindByEmailAndPasswordResult =
        Users.selectAll().where { Users.email eq form.email }.singleOrNull()?.takeIf {
            BCrypt.checkpw(form.password, it[Users.password])
        }?.mapToUserResponseForm()?.let {
            FindByEmailAndPasswordResult.Success(it)
        } ?: FindByEmailAndPasswordResult.NotFound

    fun getById(id: Long): GetByIdResult =
        Users.selectAll().where { Users.id eq id }.singleOrNull()?.mapToUserResponseForm()?.let {
            GetByIdResult.Success(it)
        } ?: GetByIdResult.NotFound

    fun create(form: UserCreateForm): CreateResult = try {
        Users.insertAndGetId {
            it[firstName] = form.firstName
            it[lastName] = form.lastName
            it[email] = form.email
            it[password] = BCrypt.hashpw(form.password, BCrypt.gensalt())
        }.value.let {
            CreateResult.Success(it)
        }
    } catch (e: SQLException) {
        CreateResult.Error(e.message ?: e.stackTraceToString()).also {
            LOGGER.error(it.toString(), e)
        }
    }

    fun updatePassword(id: Long, form: UserUpdatePasswordForm): UpdatePasswordResult = try {
        val currentPassword = Users.select(Users.password).where { Users.id eq id }.singleOrNull()?.get(Users.password)

        if (currentPassword == null) UpdatePasswordResult.Error.UserNotFound
        else
            if (!BCrypt.checkpw(form.previousPassword, currentPassword))
                UpdatePasswordResult.Error.IncorrectPreviousPassword
            else
                Users.update(where = { Users.id eq id }) {
                    it[password] = BCrypt.hashpw(form.newPassword, BCrypt.gensalt())
                }.let {
                    when (it) {
                        0 -> UpdatePasswordResult.Error.UserNotFound
                        1 -> UpdatePasswordResult.Success
                        else -> UpdatePasswordResult.Error.UpdatedCountIsIncorrect(id, it)
                    }
                }
    } catch (e: SQLException) {
        UpdatePasswordResult.Error.Unknown(e.message ?: e.stackTraceToString()).also {
            LOGGER.error(it.toString(), e)
        }
    }

    fun update(id: Long, form: UserUpdateForm): UpdateResult = try {
        Users.update({ Users.id eq form.id }) {
            it[firstName] = form.firstName
            it[lastName] = form.lastName
            it[middleName] = form.middleName
            it[email] = form.email
            it[emailVerified] = form.emailVerified
            it[passportSeries] = form.passportSeries
            it[passportNumber] = form.passportNumber
            it[phone] = form.phone
            it[phoneVerified] = form.phoneVerified
        }.let {
            when (it) {
                0 -> UpdateResult.Error.UserNotFound
                1 -> UpdateResult.Success
                else -> UpdateResult.Error.UpdatedCountIsIncorrect(id, it)
            }
        }
    } catch (e: SQLException) {
        UpdateResult.Error.Unknown(e.message ?: e.stackTraceToString()).also {
            LOGGER.error(it.toString(), e)
        }
    }

    private fun ResultRow.mapToUserResponseForm(): UserResponseForm = UserResponseForm(
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

