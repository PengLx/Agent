package com.chsteam.agent.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chsteam.agent.AgentActivity
import com.chsteam.agent.memory.Memory
import com.chsteam.agent.memory.database.vector.TextVector
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SaveWorker(context: Context, params: WorkerParameters) : Worker(context, params)  {
    override fun doWork(): Result {
        return try {

            val jsonString = inputData.getString("myKey")

            val listType = object : TypeToken<List<TextVector>>() {}.type

            val textVectors: List<TextVector> = Gson().fromJson(jsonString, listType)

            textVectors.forEach {
                AgentActivity.agentDatabase.vectorDao().insert(it)
            }
            
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}