package com.datechnologies.androidtest.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatApi
import com.datechnologies.androidtest.api.ChatLogMessageModel
import com.datechnologies.androidtest.databinding.ActivityChatBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 * Screen that displays a list of chats from a chat log.
 */
class ChatActivity : AppCompatActivity() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private lateinit var binding: ActivityChatBinding
    private var recyclerView: RecyclerView? = null
    private lateinit var chatAdapter: ChatAdapter
    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        // ActionBar
        val actionBar: ActionBar = getSupportActionBar()!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // Initialize Adapter
        chatAdapter = ChatAdapter()
        // Set LinearLayout Manager to RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = chatAdapter

        val tempList: MutableList<ChatLogMessageModel> = ArrayList<ChatLogMessageModel>()
        val chatLogMessageModel = ChatLogMessageModel()
        chatLogMessageModel.message = "This is test data. Please retrieve real data."
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        tempList.add(chatLogMessageModel)
        chatAdapter.setChatLogMessageModelList(tempList)
        Log.i("ChatActTest", "${chatLogMessageModel.message}")

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
        getChatMessages()
    }
    fun getChatMessages() {
        val job = Job()
        val coroutineScope = CoroutineScope(job + Dispatchers.Main)
        coroutineScope.launch {
            val response = ChatApi.retrofitService.getMessages()
            if (response.isSuccessful) {
                val messages = response.body()
                Log.i("ChatActTest", "Messages: ${messages?.size}")
            } else {
                Log.i("ChatActTest", "Failed!")
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object {
        //==============================================================================================
        // Static Class Methods
        //==============================================================================================
        fun start(context: Context) {
            val starter = Intent(context, ChatActivity::class.java)
            context.startActivity(starter)
        }
    }
}