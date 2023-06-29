package com.chsteam.agent.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.setting.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(Settings.OpenAI_KEY, TextRange(0, 7)))
    }

    Scaffold(topBar = {
        TopBar()
    }) { contentPadding ->
        Column(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()) {
            Column() {
                TextField(
                    value = text,
                    label = { Text(text = "ChatGPT Key")},
                    leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "Key") },
                    singleLine = true,
                    onValueChange = { textFieldValue ->
                        text = textFieldValue
                        AgentActivity.settings.saveUserSetting(Settings.API, text.text)
                    },
                    trailingIcon = {
                        IconButton(onClick = { text = TextFieldValue("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val agentViewModel : AgentViewModel = viewModel()
    TopAppBar(title = { Text("Settings")} , navigationIcon = {
        IconButton(onClick = {
            agentViewModel.currentPage.value = "Home"
        }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
        }
    })
}