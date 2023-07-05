package com.chsteam.agent.api

data class ChatFunction(
    val name: String,
    val description: String? = null,
    val parameters: Parameters
) {

    data class Parameters(
        val type: String = "object",
        val properties: Map<String, Property>? = null,
        val required: List<String>? = null
    )

    data class Property(
        val type: String,
        val enum: List<String>? = null,
        val description: String? = null
    )
}