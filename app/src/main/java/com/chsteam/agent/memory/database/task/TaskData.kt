package com.chsteam.agent.memory.database.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "status") val status: Boolean,
    @ColumnInfo(name = "description") val description : String = "Not have yet.",
    @ColumnInfo(name = "contain_message") val containMessage: List<Int>
)