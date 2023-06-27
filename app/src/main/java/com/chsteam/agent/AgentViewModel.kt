package com.chsteam.agent

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.chsteam.agent.memory.message.Message

class AgentViewModel : ViewModel() {
    private val message = mutableStateListOf<Message>()

    fun addMessage(message: Message) {
        this.message.add(message)
    }

    fun getMessage() : SnapshotStateList<Message> {
        return message
    }
}