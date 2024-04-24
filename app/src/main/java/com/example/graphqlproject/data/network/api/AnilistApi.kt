package com.example.graphqlproject.data.network.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.UserQuery
import com.example.graphqlproject.data.network.mapper.toDomain
import com.example.graphqlproject.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnilistApi @Inject constructor(
    private val apolloClient: ApolloClient
) {
    fun getUser(id: Int): Flow<User?> {
        return apolloClient
            .query(UserQuery(Optional.present(id)))
            .toFlow()
            .map {
                it.data?.User?.toDomain()
            }
    }
}