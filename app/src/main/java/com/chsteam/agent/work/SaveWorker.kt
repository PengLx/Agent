package com.chsteam.agent.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.chsteam.agent.AgentActivity
import com.chsteam.agent.memory.Memory

class SaveWorker(context: Context, params: WorkerParameters) : Worker(context, params)  {
    override fun doWork(): Result {
        return try {
            Memory.textVectors.forEach {
                AgentActivity.agentDatabase.vectorDao().insert(it)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}