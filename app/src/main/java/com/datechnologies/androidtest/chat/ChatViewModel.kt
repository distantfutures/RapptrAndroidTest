package com.datechnologies.androidtest.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datechnologies.androidtest.api.ChatApi
import com.datechnologies.androidtest.api.ChatLogMessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatViewModel : ViewModel() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _chatLog = MutableLiveData<List<ChatLogMessageModel>>()
    val chatLog: LiveData<List<ChatLogMessageModel>>
        get() = _chatLog

    private val _requestFailed = MutableLiveData<Boolean>()
    val requestFailed: LiveData<Boolean>
        get() = _requestFailed

    // Launches & receives request for chat log
    fun getChatMessages() {
        Log.i("ChatActTest", "Api Called!")
        coroutineScope.launch {
            val response = ChatApi.retrofitService.getMessages()
            try {
                val messages = response.body()
                if (messages != null) {
                    _chatLog.value = messages.data
                }
                Log.i("ChatActTest", "Messages: ${messages!!.data}")
            } catch (e: Exception) {
                _requestFailed.value = false
                Log.i("ChatActTest", "Failed!")
            }
        }
    }
}