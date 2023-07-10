package com.chsteam.agent.module.mail

enum class Mail {
    GOOGLE,
    QQ,
    OUTLOOK;

    companion object {
        val INSTANCE = GOOGLE
    }
}