package com.chsteam.agent.manager

import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.api.Role
import com.chsteam.agent.memory.Memory
import com.chsteam.agent.memory.database.history.HistoryMessage
import com.chsteam.agent.memory.message.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

object MessageManager {

    fun handleNewMessage(message: Message,agentViewModel: AgentViewModel) {

        //真实记忆添加
        Memory.tureMemory.add(message)

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        if (message.role != Role.SYSTEM && message.role != Role.FUNCTION) {
            agentViewModel.addMessage(message = message)
            //数据库操作
            //添加信息展示的数据内容
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    AgentActivity.agentDatabase.historyMessageDao().insertAll(HistoryMessage(null, message))
                }
            }
        }
        if(message.role == Role.USER) {
            Memory.send(agentViewModel)
        }
    }
}