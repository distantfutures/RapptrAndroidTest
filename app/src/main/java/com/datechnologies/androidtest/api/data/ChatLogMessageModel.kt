package com.datechnologies.androidtest.api.data

import com.google.gson.annotations.SerializedName

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */
data class ChatData (
    val data:List<ChatLogMessageModel>?
    )
data class ChatLogMessageModel (
    @SerializedName("user_id")
    var userId: Int? = 0,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("name")
    var userName: String? = null,
    @JvmField
    var message: String? = null
)