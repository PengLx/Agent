package com.chsteam.agent.manager

import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.Memory
import com.chsteam.agent.memory.database.message.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object MessageManager {

    fun handleNewMessage(message: Message, agentViewModel: AgentViewModel) {

        //真实记忆添加
        Memory.tureMemory.add(message)

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        if (message.role != Role.SYSTEM && message.role != Role.FUNCTION) {
            agentViewModel.addMessage(message = message)
            //数据库操作
            //添加信息展示的数据内容
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    AgentActivity.agentDatabase.messageDao().insertAll(message)
                }
            }
        }
        if(message.role == Role.USER) {
            Memory.send(agentViewModel)
        }
    }
}