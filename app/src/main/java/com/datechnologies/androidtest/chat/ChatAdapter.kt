package com.datechnologies.androidtest.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatLogMessageModel
import java.util.ArrayList

/**
 * A recycler view adapter used to display chat log messages in [ChatActivity].
 *
 */
class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder?>() {
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    private var chatLogMessageModelList: List<ChatLogMessageModel>

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================
    fun setChatLogMessageModelList(chatLogMessageModelList: List<ChatLogMessageModel>) {
        this.chatLogMessageModelList = chatLogMessageModelList
        notifyDataSetChanged()
    }

    //==============================================================================================
    // RecyclerView.Adapter Methods
    //==============================================================================================
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {
        val chatLogMessageModel: ChatLogMessageModel = chatLogMessageModelList[position]
        viewHolder.messageTextView.setText(chatLogMessageModel.message)
    }

    override fun getItemCount(): Int {
        return chatLogMessageModelList.size
    }

    //==============================================================================================
    // ChatViewHolder Class
    //==============================================================================================
    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImageView: ImageView
        var messageTextView: TextView

        init {
            avatarImageView = view.findViewById<View>(R.id.avatarImageView) as ImageView
            messageTextView = view.findViewById<View>(R.id.messageTextView) as TextView
        }
    }

    //==============================================================================================
    // Constructor
    //==============================================================================================
    init {
        chatLogMessageModelList = ArrayList<ChatLogMessageModel>()
    }
}