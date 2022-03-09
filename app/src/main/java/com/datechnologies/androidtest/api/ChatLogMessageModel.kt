package com.datechnologies.androidtest.api

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */
data class ChatLogMessageModel (
    var userId: Int = 0,
    var avatarUrl: String? = null,
    var username: String? = null,
    @JvmField
    var message: String? = null
)