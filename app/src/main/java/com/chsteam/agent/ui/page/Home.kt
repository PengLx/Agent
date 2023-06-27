package com.chsteam.agent.ui.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.chsteam.agent.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainPage() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var textFieldState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current


    //TODO Swap Fix
    LaunchedEffect(drawerState.isAnimationRunning) {
        keyboardController?.hide()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { ModalDrawer(scope = scope, drawerState = drawerState) },
    ) {
        Scaffold(
            topBar = { TopBar(scope, drawerState) },
            bottomBar = { ChatBottomBar(
                textFieldState,
                onTextFieldValueChange = { textFieldState = it },
            ) },
            modifier = Modifier.systemBarsPadding()
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDrawer(scope: CoroutineScope, drawerState: DrawerState) {

    val items = listOf(Icons.Default.Home, Icons.Default.Settings)
    val labels = listOf(R.string.home, R.string.settings)
    val selectedItem = remember { mutableStateOf(items[0]) }

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
    modifier: Modifier = Modifier
) {
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

            IconButton(onClick = { /*TODO*/ } , Modifier.weight(1f)) {
                Image(imageVector = Icons.Default.Send, contentDescription = "Send Button", colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary))
            }
        }
    }
}
