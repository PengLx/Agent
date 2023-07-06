package com.chsteam.agent.ui.page

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.setting.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage() {
    var text by remember {
        mutableStateOf(Settings.OpenAI_KEY)
    }

    val (checkedState, onStateChange) = remember { mutableStateOf(true) }

    val systemFunction: MutableInteractionSource = remember { MutableInteractionSource() }

    Scaffold(topBar = {
        TopBar()
    }) { contentPadding ->
        Column(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()) {
            Column {
                TextField(
                    value = text,
                    label = { Text(text = "ChatGPT Key")},
                    leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = "Key") },
                    singleLine = true,
                    onValueChange = { textFieldValue ->
                        text = textFieldValue.replace(Regex("[^a-zA-Z0-9\\-]"), "")
                        Settings.OpenAI_KEY = text
                        AgentActivity.settings.saveUserSetting(Settings.API, text)
                    },
                    trailingIcon = {
                        IconButton(onClick = { text = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .toggleable(
                            value = checkedState,
                            onValueChange = {
                                onStateChange(!checkedState)
                                AgentActivity.settings.saveUserSetting("gpt4", it)
                                Settings.UseGPT4 = it
                            },
                            role = Role.Checkbox
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = "Use ChatGPT-4",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Divider()

                Row(Modifier.fillMaxWidth()) {
                    Column() {
                        Text(text = "Clear")
                        Text(text = "Clear the database of agent")
                    }
                    Button(
                        onClick = {
                            AgentActivity.agentDatabase.clearAndResetAllTables()
                        },
                        
                    ) {
                        Text("Clear")
                    }
                }
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