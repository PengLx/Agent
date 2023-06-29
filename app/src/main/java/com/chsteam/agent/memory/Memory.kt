package com.chsteam.agent.memory

import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.message.Message

object Memory {

    //任务记忆
    val taskMemory = listOf<Message>()

    //闲聊记忆 仅在当前本次启动中记录
    val chatMemory = listOf<Message>()

    //真实记忆 真实记忆是要发送给GPT的所有信息
    val tureMemory = listOf<Message>()
}