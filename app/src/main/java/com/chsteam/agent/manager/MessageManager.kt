package com.chsteam.agent.manager

import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.message.Message
import com.chsteam.agent.setting.Settings
import com.cjcrafter.openai.OpenAI
import com.cjcrafter.openai.chat.ChatMessage.Companion.toUserMessage
import com.cjcrafter.openai.chat.ChatRequest

object MessageManager {
    fun send(message: String, viewModel: AgentViewModel) {
        val messages = mutableListOf(message.toUserMessage())
        val request = ChatRequest(model="gpt-3.5-turbo", messages=messages)

        val openai = OpenAI(Settings.OpenAI_KEY)

        openai.createChatCompletionAsync(
            request = request,
            onResponse = { response ->
                viewModel.addMessage(Message(Role.ASSISTANT, response[0].message.content)) },
            onFailure = { viewModel.addMessage(Message(Role.ASSISTANT, it.message ?: "发生错误"))}
        )
    }
}