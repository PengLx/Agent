package com.chsteam.agent.ui.page

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chsteam.agent.AgentActivity
import com.chsteam.agent.AgentViewModel
import com.chsteam.agent.R
import com.chsteam.agent.api.Role
import com.chsteam.agent.manager.MessageManager
import com.chsteam.agent.memory.database.message.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainPage() {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val keyboardController = LocalSoftwareKeyboardController.current
    val agentViewModel : AgentViewModel = viewModel()

    //TODO 键盘闪烁修复
    LaunchedEffect(drawerState.isAnimationRunning) {
        keyboardController?.hide()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawer(scope = scope, drawerState = drawerState) },
        content = {
            switchPage(scope = scope, drawerState = drawerState, page = agentViewModel.currentPage)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun switchPage(scope: CoroutineScope, drawerState: DrawerState, page: MutableState<String>) {
    when(page.value) {
        "Home" -> {
            Main(scope = scope, drawerState = drawerState)
        }
        "Setting" -> {
            SettingsPage()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(scope: CoroutineScope, drawerState: DrawerState) {
    var textFieldState by remember { mutableStateOf("") }
    val agentViewModel : AgentViewModel = viewModel()
    Scaffold(
        topBar = { TopBar(scope, drawerState) },
        bottomBar = { ChatBottomBar(
            textFieldState,
            onTextFieldValueChange = { textFieldState = it },
            onSendButtonClicked = { message ->
                textFieldState = ""
                MessageManager.handleNewMessage(Message(null, Role.USER, message, Date()), agentViewModel)
            },
            canSend = agentViewModel.canSend
        ) },
        modifier = Modifier.systemBarsPadding(),
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
        ) {
            ChatSpace(agentViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(scope: CoroutineScope, drawerState: DrawerState) {

    val items = listOf(Icons.Default.Home, Icons.Default.Settings)
    val labels = listOf(R.string.home, R.string.settings)
    val selectedItem = remember { mutableStateOf(items[0]) }
    val agentViewModel : AgentViewModel = viewModel()

    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        items.forEach { item ->
            NavigationDrawerItem(
                icon = { Icon(item, contentDescription = null) },
                label = { labels[items.indexOf(item)] },
                selected = item == selectedItem.value,
                onClick = {
                    scope.launch { drawerState.close() }
                    selectedItem.value = item
                    when(item) {
                        items[0] -> agentViewModel.currentPage.value = "Home"
                        items[1] -> agentViewModel.currentPage.value = "Setting"
                    }
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(scope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(title = { Text("Agent")} ,navigationIcon = {
        IconButton(onClick = { scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        } }) {
            Icon(Icons.Filled.Menu, contentDescription = "Menu")
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBottomBar(
    textFieldState: String,
    onTextFieldValueChange: (String) -> Unit,
    onSendButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    canSend: MutableState<Boolean>
) {
    val context = LocalContext.current
    val agentViewModel : AgentViewModel = viewModel()

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
        val speechResult = agentViewModel.handleVoiceInput(AgentViewModel.REQUEST_CODE_SPEECH_INPUT, result.resultCode, result.data)
        onTextFieldValueChange(speechResult)
    }

    Box(modifier = modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .imePadding()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment =  Alignment.CenterVertically) {
            TextField(
                value = textFieldState,
                onValueChange = onTextFieldValueChange,
                placeholder = { Text("Type a message") },
                modifier = Modifier
                    .padding(8.dp)
                    .weight(9f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (canSend.value) {
                //TODO 语言转文字
                if(true) {
                    IconButton(onClick = { if(textFieldState != "") {
                        onSendButtonClicked(textFieldState)
                        agentViewModel.canSend.value = false
                    } } , Modifier.weight(1f)) {
                        Image(imageVector = Icons.Default.Send, contentDescription = "Send Button", colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))
                    }
                } else {
                    IconButton(onClick = {
                        agentViewModel.speak(launcher) // here
                    }) {
                        Image(imageVector = Icons.Default.Call, contentDescription = "")
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
fun ChatSpace(agentViewModel : AgentViewModel) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState,modifier = Modifier.fillMaxSize()) {
        items(agentViewModel.getMessages()) { message ->
            ChatBox(message = message)
        }
    }

    LaunchedEffect(key1 = agentViewModel.getMessages().size) {
        lazyListState.animateScrollToItem(lazyListState.layoutInfo.totalItemsCount)
    }
}

@Composable
fun ChatBox(message: Message) {
    //TODO 颜色分析与选择调整
    Row(
        Modifier
            .background(if (message.role == Role.USER) Color.White else Color.LightGray)
            .fillMaxSize()
            .padding(start = 20.dp, end = 40.dp, top = 20.dp, bottom = 20.dp)
    ) {
       when(message.role) {
           Role.USER -> {
               Icon(imageVector = Icons.Default.Face, contentDescription = "User Icon")
           }
           Role.ASSISTANT -> {
               Icon(imageVector = Icons.Default.Star, contentDescription = "Assistant Icon")
           }
           else -> {}
       }
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = message.message, color = Color.Black)
    }
}