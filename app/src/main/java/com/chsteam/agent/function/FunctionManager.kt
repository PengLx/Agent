package com.chsteam.agent.function

import com.cjcrafter.openai.chat.ChatFunction

object FunctionManager {
    private val functions = mutableListOf<ChatFunction>()

    private val registerFunction = HashMap<String, Function>()

    fun getFunctions() = functions

    fun registerFunction(function: String) {
        when(function.uppercase()) {
            SystemFunction.ID -> registerFunction(SystemFunction())
        }
    }

    private fun registerFunction(function: Function) {
        functions.addAll(function.functionList)
        function.functionList.forEach {
            registerFunction[it.name] = function
        }
    }

    fun execute(name: String, vararg params: String) {
        registerFunction[name]?.execute(name, *params)
    }
}