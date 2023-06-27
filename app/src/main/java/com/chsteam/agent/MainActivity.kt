package com.chsteam.agent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.chsteam.agent.ui.page.MainPage
import com.chsteam.agent.ui.theme.AgentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //System Settings
        WindowCompat.setDecorFitsSystemWindows(window,false)


        //Content
        setContent {
            AgentTheme {
                MainPage()
            }
        }
    }
}
