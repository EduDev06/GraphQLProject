package com.example.graphqlproject.domain.repository

import com.example.graphqlproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(id: Int): Flow<User?>
}