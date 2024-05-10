package com.example.lsmapp


data class User(
    val user_id: Long,
    val username: String,
    val email: String
    // Other properties...
)

data class Post(
    val postId: Long,
    val userId: Long,
    val content: String,
    val media: String?
    // Other properties...
)

data class Comment(
    val commentId: Long,
    val postId: Long,
    val userId: Long,
    val content: String
    // Other properties...
)

data class Like(
    val likeId: Long,
    val postId: Long,
    val userId: Long
    // Other properties...
)

data class Friendship(
    val friendshipId: Long,
    val user1Id: Long,
    val user2Id: Long,
    val status: String
    // Other properties...
)

data class Notification(
    val notificationId: Long,
    val userId: Long,
    val type: String,
    val sourceId: Long
    // Other properties...
)
