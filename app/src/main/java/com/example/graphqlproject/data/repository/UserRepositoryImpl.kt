package com.example.graphqlproject.data.repository

import com.example.graphqlproject.data.network.api.AnilistApi
import com.example.graphqlproject.domain.model.User
import com.example.graphqlproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: AnilistApi
): UserRepository {
    override fun getUser(id: Int): Flow<User?> {
        return api.getUser(id)
    }
}