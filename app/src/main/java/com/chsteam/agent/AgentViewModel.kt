package com.chsteam.agent

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.message.Message
import com.cjcrafter.openai.OpenAI
import com.cjcrafter.openai.chat.ChatMessage.Companion.toUserMessage
import com.cjcrafter.openai.chat.ChatRequest
import com.cjcrafter.openai.completions.CompletionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.Consumer

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