package com.chsteam.agent.memory.database.vector

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VectorDao {
    @Query("SELECT * FROM vector")
    fun getAll(): List<Vector>

    @Insert
    fun insertAll(vararg vectors: Vector)
}
