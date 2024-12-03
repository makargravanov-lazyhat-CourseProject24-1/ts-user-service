package ru.jetlabs.ts.userservice.models

sealed interface FindByEmailAndPasswordResult {
    data class Success(val userResponseForm: UserResponseForm) : FindByEmailAndPasswordResult
    data object NotFound : FindByEmailAndPasswordResult
}

sealed interface GetByIdResult {
    data class Success(val userResponseForm: UserResponseForm) : GetByIdResult
    data object NotFound : GetByIdResult
}

sealed interface CreateResult {
    data class Success(val id: Long) : CreateResult
    sealed interface Error : CreateResult {
        val message: String

        data object IncorrectEmail : Error {
            override val message: String = "Incorrect email"
        }

        data class Unknown(override val message: String) : Error
    }
}

sealed interface UpdatePasswordResult {
    data object Success : UpdatePasswordResult

    sealed interface Error : UpdatePasswordResult {
        val message: String

        data object UserNotFound : Error {
            override val message: String = "User not found"
        }

        data object IncorrectPreviousPassword : Error {
            override val message: String = "Previous password is incorrect"
        }

        data class UpdatedCountIsIncorrect(private val id: Long, private val updatedCount: Int) : Error {
            override val message: String = "Updated count = $updatedCount. (id = $id)"
        }

        data class Unknown(override val message: String) : Error
    }
}

sealed interface UpdateResult {
    data object Success : UpdateResult

    sealed interface Error : UpdateResult {
        val message: String

        data object UserNotFound : Error {
            override val message: String = "User not found"
        }

        data class UpdatedCountIsIncorrect(private val id: Long, private val updatedCount: Int) : Error {
            override val message: String = "Updated count = $updatedCount. (id = $id)"
        }

        data class Unknown(override val message: String) : Error
    }
}