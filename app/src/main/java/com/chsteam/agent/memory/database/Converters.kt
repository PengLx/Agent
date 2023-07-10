package com.chsteam.agent.memory.database

import androidx.room.TypeConverter
import com.chsteam.agent.api.Role
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.ByteBuffer
import java.util.Date

class Converters {

    @TypeConverter
    fun fromFloatList(value: List<Float>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toFloatList(value: String): List<Float> {
        val listType = object : TypeToken<List<Float>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toRole(value: String): Role {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromRole(role: Role): String {
        return role.name
    }

    @TypeConverter
    fun fromFloatArrayToBlob(floatArray: FloatArray): ByteArray {
        val byteBuffer = ByteBuffer.allocate(4 * floatArray.size)
        for (i in floatArray.indices) {
            byteBuffer.putFloat(i * 4, floatArray[i])
        }
        return byteBuffer.array()
    }

    @TypeConverter
    fun fromBlobToFloatArray(byteArray: ByteArray): FloatArray {
        val byteBuffer = ByteBuffer.wrap(byteArray)
        val floatArray = FloatArray(byteArray.size / 4)
        for (i in floatArray.indices) {
            floatArray[i] = byteBuffer.getFloat(i * 4)
        }
        return floatArray
    }
}