package com.chsteam.agent.memory.database.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryMessageDao {
    @Query("SELECT * FROM history_message WHERE id BETWEEN :startId AND :endId")
    fun getMessageInRange(startId: Int, endId: Int): List<HistoryMessage>

    @Insert
    fun insertAll(vararg messages: HistoryMessage)
}