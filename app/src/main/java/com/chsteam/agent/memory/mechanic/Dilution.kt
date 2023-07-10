package com.chsteam.agent.memory.mechanic

import org.apache.commons.math3.ml.distance.EuclideanDistance

//该类用于淡化记忆以及清除归档记忆
class Dilution {

    // 记忆数据库，Key为唯一标识符，Value为一个Pair对象，包含内容和信息素强度
    val memoryDatabase: MutableMap<String, Pair<String, Double>> = mutableMapOf()

    // 假设我们有一个将文本转换为向量的函数
    fun textToVector(text: String): DoubleArray {
        // 这里仅为示例，实际的转换方法可能需要NLP库，例如Word2Vec, BERT等
        return doubleArrayOf()
    }

    fun updatePheromonesWithSimilarity(newMemory: Pair<String, Double>, decayRate: Double, boostRate: Double, topN: Int) {
        val newVector = textToVector(newMemory.first)

        // 计算新记忆与所有旧记忆的相似度
        val similarities = mutableMapOf<String, Double>()
        for ((key, memory) in memoryDatabase) {
            val oldVector = textToVector(memory.first)
            val distance = EuclideanDistance().compute(newVector, oldVector)  // 计算向量距离
            similarities[key] = 1 / (1 + distance)  // 计算相似度
        }

        // 找到与新记忆最相似的topN个旧记忆，并增加其信息素强度
        val topSimilarKeys =
            similarities.entries.sortedByDescending { it.value }.take(topN).map { it.key }
        for (key in topSimilarKeys) {
            val oldMemory = memoryDatabase[key]!!
            memoryDatabase[key] = Pair(oldMemory.first, oldMemory.second * (1.0 + boostRate))
        }

        // 对所有记忆进行淡化
        for (key in memoryDatabase.keys - topSimilarKeys.toSet()) {
            val oldMemory = memoryDatabase[key]!!
            memoryDatabase[key] = Pair(oldMemory.first, oldMemory.second * (1.0 - decayRate))
        }
    }
}