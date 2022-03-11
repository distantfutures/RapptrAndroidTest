package com.datechnologies.androidtest.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.databinding.ActivityChatBinding

/**
 * Screen that displays a list of chats from a chat log.
 */
class ChatActivity : AppCompatActivity() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.lifecycleOwner = this

        // ActionBar
        val actionBar: ActionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // Initialize ViewModel & Adapter to bind adapter to RecyclerView
        chatViewModel = ChatViewModel()
        chatAdapter = ChatAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }

        // Retrieves messages from server
        chatViewModel.getChatMessages()

        // Observes changes to chatLog in VM and updates recyclerView
        chatViewModel.chatLog.observe(this, Observer { log ->
            log?.let {
                chatAdapter.setChatLogMessageModelList(log)
            }
        })

        // Observes if API call failed
        chatViewModel.requestFailed.observe(this, Observer {
            if (!it) {
                Toast.makeText(this, "No Messages Retrieved!", Toast.LENGTH_SHORT).show()
            }
        })

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
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