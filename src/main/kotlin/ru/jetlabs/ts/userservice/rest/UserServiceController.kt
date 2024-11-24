package ru.jetlabs.ts.userservice.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.jetlabs.ts.userservice.models.UserCreateForm
import ru.jetlabs.ts.userservice.models.UserResponseForm
import ru.jetlabs.ts.userservice.models.UserUpdatePasswordForm
import ru.jetlabs.ts.userservice.service.UserService

const val USERS = "users"

const val ID = "id"

const val CHANGE_PASSWORD = "changepass"

@RestController
@RequestMapping("/$USERS")
class UserServiceController(
    private val userService: UserService
) {
    @GetMapping("/{$ID}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseForm> =
        userService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.noContent().build()

    @PostMapping("/$ID/$CHANGE_PASSWORD")
    fun changePassword(@RequestBody form: UserUpdatePasswordForm): ResponseEntity<Boolean> =
        userService.updatePassword(form).let { ResponseEntity.ok(it) }

    @PostMapping
    fun create(@RequestBody form: UserCreateForm): ResponseEntity<Long> =
        userService.create(form).let { ResponseEntity.ok(it) }
}