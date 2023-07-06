package com.chsteam.agent.memory.database.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    @Insert
    fun insertAll(vararg tasks: TaskData)

    @Query("SELECT * FROM task WHERE status = 0")
    fun getAllUncompletedTasks(): List<TaskData>
}