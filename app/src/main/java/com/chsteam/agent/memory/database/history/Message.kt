package com.chsteam.agent.memory.database.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.chsteam.agent.api.Role
import java.util.Date

@Entity(tableName = "history_message")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "role") val role: Role,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "data") val date : Date
)
