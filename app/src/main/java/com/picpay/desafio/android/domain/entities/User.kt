package com.picpay.desafio.android.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("img")
    val img: String,

    @SerializedName("username")
    val username: String
)