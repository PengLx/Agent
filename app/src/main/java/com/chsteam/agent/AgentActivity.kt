package com.chsteam.agent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.room.Room
import com.chsteam.agent.memory.database.AgentDatabase
import com.chsteam.agent.setting.Settings
import com.chsteam.agent.ui.page.MainPage
import com.chsteam.agent.ui.theme.AgentTheme

class AgentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //System Settings
        WindowCompat.setDecorFitsSystemWindows(window,false)

        //Content
        setContent {
            init()
            AgentTheme {
                MainPage()
            }
        }
    }

    private fun init() {
        settings = Settings(this)
        agentDatabase = Room.databaseBuilder(
            applicationContext,
            AgentDatabase::class.java, "agent-database"
        ).build()

    }

    companion object {
        lateinit var settings: Settings
        lateinit var agentDatabase : AgentDatabase
    }
}
