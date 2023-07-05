package com.chsteam.agent.memory.database.vector

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vector(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "vector") val vector: List<Float>  // 记住，你需要将vector转换为可以存储的形式，比如一个Float列表
)

