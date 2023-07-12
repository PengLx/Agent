package com.chsteam.agent.function

import com.chsteam.agent.module.mail.Mail
import com.cjcrafter.openai.chat.ChatFunction

abstract class MailFunction : Function() {

    override val name: String
        get() = "Mail"

    private val instance by lazy {
        when(Mail.INSTANCE) {
            Mail.GOOGLE -> GoogleMail()
            Mail.OUTLOOK -> OutLookMail()
            Mail.QQ -> QQMail()
        }
    }

    override val functionList: List<ChatFunction>
        get() = TODO("Not yet implemented")

    override fun execute(name: String, vararg params: String) {
        instance.sendMail()
    }

    abstract fun sendMail()

    abstract fun getMail()

    class GoogleMail : MailFunction() {
        override fun sendMail() {

        }

        override fun getMail() {

        }
    }

    class OutLookMail : MailFunction() {
        override fun sendMail() {

        }

        override fun getMail() {

        }
    }

    class QQMail : MailFunction() {
        override fun sendMail() {

        }

        override fun getMail() {

        }
    }
}