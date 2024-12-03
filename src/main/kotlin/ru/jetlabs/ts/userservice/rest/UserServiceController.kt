package ru.jetlabs.ts.userservice.rest

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
        userService.findByEmailAndPassword(form).let {
            when (it) {
                is FindByEmailAndPasswordResult.Success -> ResponseEntity.ok(it.userResponseForm)
                FindByEmailAndPasswordResult.NotFound -> ResponseEntity.notFound().build()
            }
        }

    @PostMapping("/create")
    fun create(@RequestBody form: UserCreateForm): ResponseEntity<*> =
        userService.create(form).let {
            when (it) {
                is CreateResult.Success -> ResponseEntity.ok().build()
                is CreateResult.Error.Unknown -> ResponseEntity.internalServerError().body(it.message)
                is CreateResult.Error -> ResponseEntity.badRequest().body(it.message)
            }
        }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseForm> = userService.getById(id).let {
        when (it) {
            is GetByIdResult.Success -> ResponseEntity.ok(it.userResponseForm)
            GetByIdResult.NotFound -> ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody form: UserUpdateForm): ResponseEntity<*> =
        userService.update(id, form).let {
            when (it) {
                UpdateResult.Success -> ResponseEntity.ok().build()
                is UpdateResult.Error.Unknown -> ResponseEntity.internalServerError().body(it.message)
                is UpdateResult.Error -> ResponseEntity.badRequest().body(it.message)
            }
        }

    @PostMapping("/{id}/change-password")
    fun changePassword(@PathVariable id: Long, @RequestBody form: UserUpdatePasswordForm): ResponseEntity<*> =
        userService.updatePassword(id, form).let {
            when (it) {
                UpdatePasswordResult.Success -> ResponseEntity.ok().build()
                is UpdatePasswordResult.Error.Unknown -> ResponseEntity.internalServerError().body(it.message)
                is UpdatePasswordResult.Error -> ResponseEntity.badRequest().body(it.message)
            }
        }
}