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
        INSTANCE = this
        settings = Settings(this)
        //TODO 发布时需要移除开发模式语句
        agentDatabase = Room.databaseBuilder(
            applicationContext,
            AgentDatabase::class.java, "agent-database"
        ).fallbackToDestructiveMigration().build()
    }

    companion object {
        lateinit var INSTANCE : ComponentActivity
        lateinit var settings: Settings
        lateinit var agentDatabase : AgentDatabase
    }
}
