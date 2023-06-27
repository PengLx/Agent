package com.chsteam.agent.memory.database.history

import androidx.room.TypeConverter
import com.chsteam.agent.memory.message.Message
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromMessage(message: Message): String {
        return Gson().toJson(message)
    }

    @TypeConverter
    fun toAddress(message: String): Message {
        return Gson().fromJson(message, Message::class.java)
    }
}