package com.chsteam.agent

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.chsteam.agent.memory.database.message.Message
import java.util.Locale

class AgentViewModel : ViewModel() {
    private val messages = mutableStateListOf<Message>()

    val currentPage = mutableStateOf("Home")

    val canSend = mutableStateOf(true)

    fun addMessage(message: Message) {
        messages.add(message)
    }

    fun getMessages(): SnapshotStateList<Message> {
        return messages
    }

    fun speak(launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请说话")

        try {
            launcher.launch(intent)
        } catch (e: Exception) {
            // handle error here
        }
    }

    fun handleVoiceInput(requestCode: Int, resultCode: Int, data: Intent?): String {
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            return result?.get(0) ?: ""
        }
        return ""
    }

    companion object {
        const val REQUEST_CODE_SPEECH_INPUT = 100
    }
}