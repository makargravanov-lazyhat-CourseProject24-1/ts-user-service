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

const val VERSION_1 = "v1"

@RestController
@RequestMapping("$VERSION_1")
class UserServiceController(
    private val userService: UserService
) {
    @PostMapping("/find")
    fun getByEmailAndPassword(@RequestBody form: UserFindForm): ResponseEntity<UserResponseForm> =
        userService.findByEmailAndPassword(form)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @GetMapping("/{$ID}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseForm> =
        userService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.noContent().build()

    @PostMapping("/$ID/$CHANGE_PASSWORD")
    fun changePassword(@RequestBody form: UserUpdatePasswordForm): ResponseEntity<Boolean> =
        userService.updatePassword(form).let { ResponseEntity.ok(it) }

    @PostMapping("/create")
    fun create(@RequestBody form: UserCreateForm): ResponseEntity<Nothing> = ResponseEntity.noContent().build()
}

data class UserFindForm(val email: String, val password: String)