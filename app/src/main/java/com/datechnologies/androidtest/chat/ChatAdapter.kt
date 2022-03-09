package com.datechnologies.androidtest.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.datechnologies.androidtest.R
import com.datechnologies.androidtest.api.ChatLogMessageModel
import com.squareup.picasso.Picasso
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
    private fun bindAvatarImage(
        chatLogMessageModel: ChatLogMessageModel,
        viewHolder: ChatViewHolder
    ) {
        val url = chatLogMessageModel.avatarUrl
        Picasso.get()
            .load(url)
            .into(viewHolder.avatarImageView)
    }

    //==============================================================================================
    // RecyclerView.Adapter Methods
    //==============================================================================================
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {
        val chatLogMessageModel: ChatLogMessageModel = chatLogMessageModelList[position]
        viewHolder.messageTextView.text = chatLogMessageModel.message
        viewHolder.userNameTextView.text = chatLogMessageModel.userName
        bindAvatarImage(chatLogMessageModel, viewHolder)
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
        var userNameTextView: TextView

        init {
            avatarImageView = view.findViewById<View>(R.id.avatarImageView) as ImageView
            messageTextView = view.findViewById<View>(R.id.messageTextView) as TextView
            userNameTextView = view.findViewById<View>(R.id.userNameTextView) as TextView
        }
    }

    //==============================================================================================
    // Constructor
    //==============================================================================================
    init {
        chatLogMessageModelList = ArrayList<ChatLogMessageModel>()
    }
}