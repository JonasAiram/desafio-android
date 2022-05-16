package com.picpay.desafio.android.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.domain.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListUsers(listUsers: List<User>?)

    @Insert()
    fun insertUser(user: User)

    @Query("SELECT * FROM user")
    fun loadAllRepository(): Flow<List<User>>

}