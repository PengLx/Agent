package com.chsteam.agent.memory.message

import com.chsteam.agent.api.Role
import java.util.Date

data class Message(val role: Role, var message: String, val date: Date)
