package com.chsteam.agent.memory.database

import androidx.room.TypeConverter
import com.chsteam.agent.api.Role
import com.chsteam.agent.gson.DateAdapter
import com.chsteam.agent.gson.RoleAdapter
import com.chsteam.agent.memory.message.Message
import com.google.gson.GsonBuilder
import java.util.Date

class Converters {
    @TypeConverter
    fun fromMessage(message: Message): String {
        return GsonBuilder().registerTypeAdapter(Role::class.java, RoleAdapter()).registerTypeAdapter(Date::class.java, DateAdapter()).create().toJson(message)
    }

    @TypeConverter
    fun toMessage(message: String): Message {
        return GsonBuilder().registerTypeAdapter(Role::class.java, RoleAdapter()).registerTypeAdapter(Date::class.java, DateAdapter()).create().fromJson(message, Message::class.java)
    }
}