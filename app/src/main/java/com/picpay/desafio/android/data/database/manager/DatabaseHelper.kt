package com.picpay.desafio.android.data.database.manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.database.dao.UserDAO
import com.picpay.desafio.android.domain.entities.User

@Database(entities = [User::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun userDao() : UserDAO

    companion object {
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper? {
            if (INSTANCE == null) {
                synchronized(DatabaseHelper::class) {
                    INSTANCE = Room.databaseBuilder(context, DatabaseHelper::class.java, "contacts_db")
                        .allowMainThreadQueries()  //TODO: depois retirar para testar
                        .build()
                }
            }
            return INSTANCE
        }
    }
}