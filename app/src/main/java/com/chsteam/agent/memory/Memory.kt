package com.chsteam.agent.memory

import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.api.Role
import com.chsteam.agent.manager.MessageManager
import com.chsteam.agent.memory.database.message.Message
import com.chsteam.agent.setting.Settings
import com.cjcrafter.openai.OpenAI
import com.cjcrafter.openai.chat.ChatMessage
import com.cjcrafter.openai.chat.ChatMessage.Companion.toAssistantMessage
import com.cjcrafter.openai.chat.ChatMessage.Companion.toSystemMessage
import com.cjcrafter.openai.chat.ChatMessage.Companion.toUserMessage
import com.cjcrafter.openai.chat.ChatRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

object Memory {

    //真实记忆 真实记忆是要发送给GPT的所有信息
    val tureMemory = mutableListOf<Message>()

    fun send(viewModel: AgentViewModel) {

        val messages = tureMemory.map { message ->
            when (message.role) {
                Role.USER -> message.message.toUserMessage()
                Role.ASSISTANT -> message.message.toAssistantMessage()
                Role.SYSTEM -> message.message.toSystemMessage()
                Role.FUNCTION -> {}
            }
        }

        val request = ChatRequest(model="gpt-3.5-turbo", messages= messages as MutableList<ChatMessage>)

        val openai = OpenAI(Settings.OpenAI_KEY)

        openai.createChatCompletionAsync(
            request = request,
            onResponse = { response ->
                MessageManager.handleNewMessage(Message(null, Role.ASSISTANT, response[0].message.content, Date()) , viewModel)
                viewModel.canSend.value = true },
            onFailure = {
                viewModel.addMessage(Message(null, Role.ASSISTANT, it.message ?: "发生错误", Date()))
                viewModel.canSend.value = true
            }
        )
    }

    fun pullFormTask() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val taskList = AgentActivity.agentDatabase.taskDao().getAllUncompletedTasks()

                val allContainMessages: List<Int> = taskList.flatMap { it.containMessage }

                allContainMessages.sorted().forEach { id ->
                    AgentActivity.agentDatabase.messageDao().getMessageById(id)?.let { message ->
                        tureMemory.add(message)
                    }
                }
            }
        }
    }
}