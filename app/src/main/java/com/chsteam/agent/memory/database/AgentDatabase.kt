package com.chsteam.agent.memory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chsteam.agent.memory.database.history.HistoryMessage
import com.chsteam.agent.memory.database.history.HistoryMessageDao

@Database(entities = [HistoryMessage::class], version = 1)
@TypeConverters(Converters::class)
abstract class AgentDatabase : RoomDatabase() {
    abstract fun historyMessageDao() : HistoryMessageDao
}