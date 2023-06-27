package com.chsteam.agent.memory

import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.message.Message

object Memory {
    //作为缓冲池 从数据库拉取AI与用户所有的聊天记录
    //展示属性更多
    val longTermMemory = mutableListOf<Message>(
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "你好"),
        Message(Role.ASSISTANT, "你好, 我是Agent"),
        Message(Role.USER, "超长文本测试"),
        Message(Role.ASSISTANT, "你好, 我草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草草"),
    )

    //任务记忆
    val taskMemory = listOf<Message>()

    //闲聊记忆 仅在当前本次启动中记录
    val chatMemory = listOf<Message>()

    //真实记忆 真实记忆是要发送给GPT的所有信息
    val tureMemory = listOf<Message>()
}