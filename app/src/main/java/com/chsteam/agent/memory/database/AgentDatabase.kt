package com.chsteam.agent.memory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chsteam.agent.memory.database.history.HistoryMessage
import com.chsteam.agent.memory.database.history.HistoryMessageDao
import com.chsteam.agent.memory.database.task.TaskData
import com.chsteam.agent.memory.database.vector.Vector
import com.chsteam.agent.memory.database.vector.VectorDao

@Database(entities = [HistoryMessage::class, Vector::class, TaskData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AgentDatabase : RoomDatabase() {
    abstract fun historyMessageDao() : HistoryMessageDao

    abstract fun vectorDao() : VectorDao
}