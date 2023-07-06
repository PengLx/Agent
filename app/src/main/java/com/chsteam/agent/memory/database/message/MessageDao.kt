package com.chsteam.agent.memory.database.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {
    @Query("SELECT * FROM history_message WHERE id BETWEEN :startId AND :endId")
    fun getMessageInRange(startId: Int, endId: Int): List<Message>

    @Insert
    fun insertAll(vararg messages: Message)

    @Query("SELECT * FROM history_message WHERE id = :id")
    fun getMessageById(id: Int): Message?
}