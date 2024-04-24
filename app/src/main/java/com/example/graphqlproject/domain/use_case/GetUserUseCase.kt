package com.example.graphqlproject.domain.use_case

import com.example.graphqlproject.domain.model.User
import com.example.graphqlproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(id: Int): Flow<Result<User?>> {
        return repository
            .getUser(id)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}