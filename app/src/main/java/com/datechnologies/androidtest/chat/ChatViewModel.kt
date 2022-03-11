package com.datechnologies.androidtest.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.datechnologies.androidtest.api.AppApi
import com.datechnologies.androidtest.api.data.ChatLogMessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _chatLog = MutableLiveData<List<ChatLogMessageModel>>()
    val chatLog: LiveData<List<ChatLogMessageModel>>
        get() = _chatLog

    private val _requestFailed = MutableLiveData<Boolean>()
    val requestFailed: LiveData<Boolean>
        get() = _requestFailed

    init {
        getChatMessages()
    }

    // Launches & receives request for chat log
    private fun getChatMessages() {
        Log.i("ChatActTest", "Api Called!")
        coroutineScope.launch {
            val response = AppApi.RETROFIT_SERVICE.getMessages()
            if (response.isSuccessful) {
                val messages = response.body()
                _chatLog.value = messages!!.data
                Log.i("ChatActTest", "Messages Check: ${response}")
            } else {
                _requestFailed.value = false
                Log.i("ChatActTest", "Failed!")
            }
        }
    }
}