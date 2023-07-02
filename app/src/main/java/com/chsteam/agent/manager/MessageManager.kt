package com.chsteam.agent.manager

import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.Memory
import com.chsteam.agent.memory.database.history.HistoryMessage
import com.chsteam.agent.memory.message.Message

object MessageManager {

    fun handleNewMessage(message: Message,agentViewModel: AgentViewModel) {

        //真实记忆添加
        Memory.tureMemory.add(message)

        if (message.role != Role.SYSTEM) {
            agentViewModel.addMessage(message = message)
            //数据库操作
            //添加信息展示的数据内容
            AgentActivity.agentDatabase.historyMessageDao().insertAll(HistoryMessage(null, message))
        }
        if(message.role == Role.USER) {
            Memory.send(agentViewModel)
        }
    }
}