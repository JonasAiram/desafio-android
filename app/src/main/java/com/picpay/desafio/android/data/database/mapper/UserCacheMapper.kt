package com.picpay.desafio.android.data.database.mapper

import com.picpay.desafio.android.data.database.model.UserCache
import com.picpay.desafio.android.domain.entities.User

object UserCacheMapper {

    fun map(cacheData: List<UserCache>) = cacheData.map { map(it) }

    private fun map(cacheData: UserCache) = User(
        id = cacheData.id,
        name = cacheData.name,
        img = cacheData.img,
        username = cacheData.username
    )

    fun mapUsersToCache(user: List<User>) = user.map { map(it) }

    private fun map(data: User) = UserCache(
        id = data.id,
        name = data.name,
        img = data.img,
        username = data.username
    )
}