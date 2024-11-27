package ru.jetlabs.ts.userservice.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.jetlabs.ts.userservice.models.UserCreateForm
import ru.jetlabs.ts.userservice.models.UserFindForm
import ru.jetlabs.ts.userservice.models.UserResponseForm
import ru.jetlabs.ts.userservice.models.UserUpdatePasswordForm
import ru.jetlabs.ts.userservice.service.UserService

@RestController
@RequestMapping("/user-service/api/v1")
class UserServiceController(
    private val userService: UserService
) {
    @PostMapping("/find")
    fun getByEmailAndPassword(@RequestBody form: UserFindForm): ResponseEntity<UserResponseForm> =
        userService.findByEmailAndPassword(form)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping("/create")
    fun create(@RequestBody form: UserCreateForm): ResponseEntity<Nothing> =
        userService.create(form).let { ResponseEntity.noContent().build() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseForm> =
        userService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.noContent().build()

    @PostMapping("/{id}/change-password")
    fun changePassword(@PathVariable id: Long, @RequestBody form: UserUpdatePasswordForm): ResponseEntity<Boolean> =
        userService.updatePassword(id, form).let { ResponseEntity.ok(it) }
}