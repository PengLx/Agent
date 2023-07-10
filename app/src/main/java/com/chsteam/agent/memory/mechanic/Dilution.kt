package com.chsteam.agent.memory.mechanic

import com.chsteam.agent.memory.database.vector.TextVector
import org.apache.commons.math3.ml.distance.EuclideanDistance
import com.chsteam.agent.memory.Memory.textVectors

//该类用于淡化记忆以及清除归档记忆
class Dilution {

    fun updatePheromones(newMemory: TextVector, decayRate: Double, distanceFactor: Double) {
        // 计算新记忆与所有旧记忆的距离
        val distances = mutableMapOf<Int, Double>()
        for (oldMemory in textVectors) {
            val distance = EuclideanDistance().compute(newMemory.vector.map { it.toDouble() }.toDoubleArray(), oldMemory.vector.map { it.toDouble() }.toDoubleArray())
            distances[oldMemory.id] = distance
        }

        // 根据距离调整信息素强度，并进行淡化
        for (oldMemory in textVectors) {
            val distance = distances[oldMemory.id]!!
            val pheromoneAdjustFactor = 1 / (1 + distance * distanceFactor)  // 根据距离计算信息素强度调整因子
            oldMemory.pheromones = oldMemory.pheromones * pheromoneAdjustFactor * (1.0 - decayRate)
        }
    }
}