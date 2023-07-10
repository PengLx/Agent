package com.chsteam.agent.memory.database.vector

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chsteam.agent.memory.database.message.Message

@Entity(
    foreignKeys = [ForeignKey(
        entity = Message::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TextVector(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "vector") val vector: FloatArray,
    @ColumnInfo(name = "pheromones") var pheromones: Double,
)