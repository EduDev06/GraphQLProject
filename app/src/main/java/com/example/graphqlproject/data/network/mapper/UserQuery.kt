package com.example.graphqlproject.data.network.mapper

import com.example.UserQuery
import com.example.graphqlproject.domain.model.Avatar
import com.example.graphqlproject.domain.model.User

fun UserQuery.User.toDomain() = User(
    name = name,
    bannerImage = bannerImage.orEmpty(),
    avatar = avatar?.toDomain() ?: throw Exception("Avatar cannot be null")
)

private fun UserQuery.Avatar.toDomain() = Avatar(
    medium = medium.orEmpty()
)