package com.example.lsmapp



object DatabaseContract {
    // Define constants for table names and column names
    object UserEntry {
        const val TABLE_NAME = "User"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAME = "name"
        const val COLUMN_PROFILE_PICTURE = "profile_picture"
        const val COLUMN_BIO = "bio"
        const val COLUMN_CREATED_AT = "created_at"
        const val COLUMN_LAST_UPDATED_AT = "last_updated_at"
    }

    object PostEntry {
        const val TABLE_NAME = "Post"
        const val COLUMN_POST_ID = "post_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_MEDIA = "media"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    object CommentEntry {
        const val TABLE_NAME = "Comment"
        const val COLUMN_COMMENT_ID = "comment_id"
        const val COLUMN_POST_ID = "post_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    object LikeEntry {
        const val TABLE_NAME = "Like"
        const val COLUMN_LIKE_ID = "like_id"
        const val COLUMN_POST_ID = "post_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    object FriendshipEntry {
        const val TABLE_NAME = "Friendship"
        const val COLUMN_FRIENDSHIP_ID = "friendship_id"
        const val COLUMN_USER1_ID = "user1_id"
        const val COLUMN_USER2_ID = "user2_id"
        const val COLUMN_STATUS = "status"
        const val COLUMN_TIMESTAMP = "timestamp"
    }

    object NotificationEntry {
        const val TABLE_NAME = "Notification"
        const val COLUMN_NOTIFICATION_ID = "notification_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_TYPE = "type"
        const val COLUMN_SOURCE_ID = "source_id"
        const val COLUMN_TIMESTAMP = "timestamp"
    }
}
