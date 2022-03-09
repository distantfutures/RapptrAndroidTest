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

class ChatViewModel : ViewModel() {
    val job = Job()
    val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _chatLog = MutableLiveData<List<ChatLogMessageModel>>()
    val chatLog: LiveData<List<ChatLogMessageModel>>
        get() = _chatLog

    init {
        getChatMessages()
    }

    fun getChatMessages() {
        Log.i("ChatActTest", "Api Called!")
        coroutineScope.launch {
            val response = ChatApi.retrofitService.getMessages()
            if (response.isSuccessful) {
                val messages = response.body()
                if (messages != null) {
                    _chatLog.value = messages.data
                }
                Log.i("ChatActTest", "Messages: ${messages!!.data}")
            } else {
                Log.i("ChatActTest", "Failed!")
            }
        }
    }
}