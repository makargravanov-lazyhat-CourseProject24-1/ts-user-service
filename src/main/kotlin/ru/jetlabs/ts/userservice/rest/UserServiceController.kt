package ru.jetlabs.ts.userservice.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.jetlabs.ts.userservice.models.*
import ru.jetlabs.ts.userservice.service.UserService

@RestController
@RequestMapping("/ts-user-service/api/v1")
class UserServiceController(
    private val userService: UserService
) {
    @PostMapping("/find")
    fun getByEmailAndPassword(@RequestBody form: UserFindForm): ResponseEntity<UserResponseForm> =
        userService.findByEmailAndPassword(form)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()

    @PostMapping("/create")
    fun create(@RequestBody form: UserCreateForm): ResponseEntity<Nothing> =
        userService.create(form).let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseForm> =
        userService.getById(id)?.let { ResponseEntity.ok(it) } ?: ResponseEntity.noContent().build()

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody form: UserUpdateForm): ResponseEntity<Nothing> =
        userService.update(id, form).let { if(it) ResponseEntity.ok().build() else ResponseEntity.badRequest().build() }

    @PostMapping("/{id}/change-password")
    fun changePassword(@PathVariable id: Long, @RequestBody form: UserUpdatePasswordForm): ResponseEntity<Boolean> =
        userService.updatePassword(id, form).let { ResponseEntity.ok(it) }
}