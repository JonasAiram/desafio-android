package com.picpay.desafio.android.data.database.dao

import androidx.room.*
import com.picpay.desafio.android.data.database.model.UserCache
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listUsers: List<UserCache>)

    @Insert()
    fun insertUser(user: UserCache)

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<UserCache>>

    @Query("DELETE FROM user")
    fun deleteAll()

    @Transaction
    fun updateData(users: List<UserCache>) {
        deleteAll()
        insertAll(users)
    }

}