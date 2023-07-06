package com.chsteam.agent.memory.database.vector

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chsteam.agent.memory.database.message.Message

@Entity(foreignKeys = [
    ForeignKey(
        entity = Message::class,
        parentColumns = ["id"],
        childColumns = ["message"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Vector(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "message") val message: Int,
    @ColumnInfo(name = "vector") val vector: List<Float>
)

