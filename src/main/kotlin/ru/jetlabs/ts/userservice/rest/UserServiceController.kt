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
    fun getByEmailAndPassword(@RequestBody form: UserFindForm): ResponseEntity<*> =
        userService.findByEmailAndPassword(form).let {
            when (it) {
                is FindByEmailAndPasswordResult.Success -> ResponseEntity.status(HttpStatus.OK)
                    .body(it.userResponseForm)

                is FindByEmailAndPasswordResult.NotFound -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(it)
            }
        }

    @PostMapping("/create")
    fun create(@RequestBody form: UserCreateForm): ResponseEntity<*> =
        userService.create(form).let {
            when (it) {
                is CreateResult.Success -> ResponseEntity.status(HttpStatus.OK).build()
                is CreateResult.Error.Unknown -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message)
                is CreateResult.Error -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message)
            }
        }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<*> = userService.getById(id).let {
        when (it) {
            is GetByIdResult.Success -> ResponseEntity.status(HttpStatus.OK).body(it.userResponseForm)
            is GetByIdResult.NotFound -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Long, @RequestBody form: UserUpdateForm): ResponseEntity<*> =
        userService.update(id, form).let {
            when (it) {
                is UpdateResult.Success -> ResponseEntity.status(HttpStatus.OK).build()
                is UpdateResult.Error.Unknown -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message)
                is UpdateResult.Error -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message)
            }
        }

    @PostMapping("/{id}/change-password")
    fun changePassword(@PathVariable id: Long, @RequestBody form: UserUpdatePasswordForm): ResponseEntity<*> =
        userService.updatePassword(id, form).let {
            when (it) {
                is UpdatePasswordResult.Success -> ResponseEntity.status(HttpStatus.OK).build()
                is UpdatePasswordResult.Error.Unknown -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message)
                is UpdatePasswordResult.Error -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(it.message)
            }
        }
}