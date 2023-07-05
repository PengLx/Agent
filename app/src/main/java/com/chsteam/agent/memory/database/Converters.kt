package com.chsteam.agent.memory.database

import androidx.room.TypeConverter
import com.chsteam.agent.api.Role
import com.chsteam.agent.gson.DateAdapter
import com.chsteam.agent.gson.RoleAdapter
import com.chsteam.agent.memory.message.Message
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
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

    @TypeConverter
    fun fromFloatList(value: List<Float>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toFloatList(value: String): List<Float> {
        val listType = object : TypeToken<List<Float>>() {}.type
        return Gson().fromJson(value, listType)
    }
}