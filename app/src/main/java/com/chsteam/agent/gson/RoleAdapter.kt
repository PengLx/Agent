package com.chsteam.agent.gson

import com.chsteam.agent.api.Role
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class RoleAdapter : TypeAdapter<Role?>() {
    override fun write(writer: JsonWriter, value: Role?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.name.lowercase())
        }
    }

    override fun read(reader: JsonReader): Role? {
        return if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            null
        } else {
            val name = reader.nextString()
            Role.valueOf(name.uppercase())
        }
    }
}