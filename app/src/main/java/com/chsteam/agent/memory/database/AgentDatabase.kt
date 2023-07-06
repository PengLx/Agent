package com.chsteam.agent.memory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chsteam.agent.memory.database.history.Message
import com.chsteam.agent.memory.database.history.MessageDao
import com.chsteam.agent.memory.database.task.TaskDao
import com.chsteam.agent.memory.database.task.TaskData
import com.chsteam.agent.memory.database.vector.Vector
import com.chsteam.agent.memory.database.vector.VectorDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [Message::class, Vector::class, TaskData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AgentDatabase : RoomDatabase() {
    abstract fun messageDao() : MessageDao

    abstract fun vectorDao() : VectorDao

    abstract fun taskDao() : TaskDao

    fun clearAndResetAllTables() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                clearAllTables()
            }
        }
    }
}