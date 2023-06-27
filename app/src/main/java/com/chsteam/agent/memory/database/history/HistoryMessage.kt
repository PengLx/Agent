package com.chsteam.agent.memory.database.history

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chsteam.agent.memory.message.Message

import androidx.room.Insert

@Entity(tableName = "history_message")
data class HistoryMessage(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "message") val message: Message
)
