package com.chsteam.agent.memory.database.vector

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TextVectorDao {
    @Query("SELECT * FROM TextVector")
    fun getAll(): List<TextVector>

    @Query("SELECT * FROM TextVector WHERE id = :id")
    fun getById(id: Int): TextVector?

    @Query("SELECT * FROM TextVector WHERE pheromones BETWEEN :minPheromones AND :maxPheromones")
    fun getByPheromonesRange(minPheromones: Double, maxPheromones: Double): List<TextVector>

    @Query("SELECT * FROM TextVector WHERE pheromones >= 0.1")
    fun getTrueMemory(): List<TextVector>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(textVector: TextVector)

    @Update
    fun update(textVector: TextVector)

    @Delete
    fun delete(textVector: TextVector)
}