package com.chsteam.agent.function

import com.cjcrafter.openai.chat.ChatFunction

abstract class Function {

    abstract val functionList : List<ChatFunction>

    abstract fun execute(name: String, vararg params: String)

    fun pushResult(string: String) {

    }
}