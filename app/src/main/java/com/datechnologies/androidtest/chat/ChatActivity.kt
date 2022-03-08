package com.datechnologies.androidtest.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.datechnologies.androidtest.MainActivity
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatLogMessageModel
import java.util.ArrayList

/**
 * Screen that displays a list of chats from a chat log.
 */
class ChatActivity : AppCompatActivity() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private var recyclerView: RecyclerView? = null
    private var chatAdapter: ChatAdapter? = null

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        recyclerView = findViewById(R.id.recyclerView) as RecyclerView?
        val actionBar: ActionBar = getSupportActionBar()!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        chatAdapter = ChatAdapter()
        recyclerView?.setAdapter(chatAdapter)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        )
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
        chatAdapter!!.setChatLogMessageModelList(tempList)

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