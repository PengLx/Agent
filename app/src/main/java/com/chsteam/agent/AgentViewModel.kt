package com.chsteam.agent

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.chsteam.agent.memory.database.history.Message

class AgentViewModel : ViewModel() {
    private val messages = mutableStateListOf<Message>()

    val currentPage = mutableStateOf("Home")

    val canSend = mutableStateOf(true)

    fun addMessage(message: Message) {
        messages.add(message)
    }

    fun getMessages(): SnapshotStateList<Message> {
        return messages
    }
}