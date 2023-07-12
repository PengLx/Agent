package com.chsteam.agent.function

import com.cjcrafter.openai.chat.ChatFunction
import java.util.TreeMap
import kotlin.reflect.KClass

object FunctionManager {
    //每次发送时给GPT的ChatFunction
    private val functions = mutableListOf<ChatFunction>()

    //存储 可执行的函数名 和 函数属于哪个类
    private val registerFunction = HashMap<String, KClass<out Function>>()

    //可用的Functions 只要关联上的都会被写进这个
    private val allFunctions = TreeMap<String, KClass<out Function>>(String.CASE_INSENSITIVE_ORDER)

    fun register(id: String, function: KClass<out Function>) {
        allFunctions[id] = function
    }

    fun getFunctions() = functions

    fun getAllFunctions() = allFunctions

    fun registerFunction(function: String) {
        allFunctions[function]?.constructors?.firstOrNull()?.call()?.let { registerFunction(it) }
    }

    private fun registerFunction(function: Function) {
        functions.addAll(function.functionList)
        function.functionList.forEach {
            registerFunction[it.name] = function::class
        }
    }

    fun execute(name: String, vararg params: String) {
        val function = registerFunction[name]?.constructors?.firstOrNull()?.call()
        function?.execute(name, *params)
    }

    init {
        register(SystemFunction().name, SystemFunction::class)
        register(MailFunction.instance.name, MailFunction::class)
    }
}