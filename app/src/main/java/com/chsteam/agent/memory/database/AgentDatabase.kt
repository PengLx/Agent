package com.chsteam.agent.memory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chsteam.agent.memory.database.message.Message
import com.chsteam.agent.memory.database.message.MessageDao
import com.chsteam.agent.memory.database.task.TaskDao
import com.chsteam.agent.memory.database.task.TaskData
import com.chsteam.agent.memory.database.vector.TextVector
import com.chsteam.agent.memory.database.vector.TextVectorDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [Message::class, TextVector::class, TaskData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AgentDatabase : RoomDatabase() {
    abstract fun messageDao() : MessageDao

    abstract fun vectorDao() : TextVectorDao

    abstract fun taskDao() : TaskDao

    fun clearAndResetAllTables() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                clearAllTables()
            }
        }
    }
}