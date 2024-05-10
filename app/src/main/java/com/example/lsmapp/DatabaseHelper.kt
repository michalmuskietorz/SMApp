package com.example.lsmapp


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create User table
        val createUserTable = """
            CREATE TABLE ${DatabaseContract.UserEntry.TABLE_NAME} (
                ${DatabaseContract.UserEntry.COLUMN_USER_ID} INTEGER PRIMARY KEY,
                ${DatabaseContract.UserEntry.COLUMN_USERNAME} TEXT UNIQUE NOT NULL,
                ${DatabaseContract.UserEntry.COLUMN_EMAIL} TEXT UNIQUE NOT NULL,
                ${DatabaseContract.UserEntry.COLUMN_PASSWORD} TEXT NOT NULL,
                ${DatabaseContract.UserEntry.COLUMN_NAME} TEXT,
                ${DatabaseContract.UserEntry.COLUMN_PROFILE_PICTURE} TEXT,
                ${DatabaseContract.UserEntry.COLUMN_BIO} TEXT,
                ${DatabaseContract.UserEntry.COLUMN_CREATED_AT} DATETIME DEFAULT CURRENT_TIMESTAMP,
                ${DatabaseContract.UserEntry.COLUMN_LAST_UPDATED_AT} DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent()
        db.execSQL(createUserTable)

        // Create Post table
        val createPostTable = """
            CREATE TABLE ${DatabaseContract.PostEntry.TABLE_NAME} (
                ${DatabaseContract.PostEntry.COLUMN_POST_ID} INTEGER PRIMARY KEY,
                ${DatabaseContract.PostEntry.COLUMN_USER_ID} INTEGER,
                ${DatabaseContract.PostEntry.COLUMN_CONTENT} TEXT,
                ${DatabaseContract.PostEntry.COLUMN_MEDIA} TEXT,
                ${DatabaseContract.PostEntry.COLUMN_TIMESTAMP} DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (${DatabaseContract.PostEntry.COLUMN_USER_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID})
            )
        """.trimIndent()
        db.execSQL(createPostTable)

        // Create Comment table
        val createCommentTable = """
            CREATE TABLE ${DatabaseContract.CommentEntry.TABLE_NAME} (
                ${DatabaseContract.CommentEntry.COLUMN_COMMENT_ID} INTEGER PRIMARY KEY,
                ${DatabaseContract.CommentEntry.COLUMN_POST_ID} INTEGER,
                ${DatabaseContract.CommentEntry.COLUMN_USER_ID} INTEGER,
                ${DatabaseContract.CommentEntry.COLUMN_CONTENT} TEXT,
                ${DatabaseContract.CommentEntry.COLUMN_TIMESTAMP} DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (${DatabaseContract.CommentEntry.COLUMN_POST_ID}) REFERENCES ${DatabaseContract.PostEntry.TABLE_NAME}(${DatabaseContract.PostEntry.COLUMN_POST_ID}),
                FOREIGN KEY (${DatabaseContract.CommentEntry.COLUMN_USER_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID})
            )
        """.trimIndent()
        db.execSQL(createCommentTable)

        // Create Like table
        val createLikeTable = """
            CREATE TABLE ${DatabaseContract.LikeEntry.TABLE_NAME} (
                ${DatabaseContract.LikeEntry.COLUMN_LIKE_ID} INTEGER PRIMARY KEY,
                ${DatabaseContract.LikeEntry.COLUMN_POST_ID} INTEGER,
                ${DatabaseContract.LikeEntry.COLUMN_USER_ID} INTEGER,
                ${DatabaseContract.LikeEntry.COLUMN_TIMESTAMP} DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (${DatabaseContract.LikeEntry.COLUMN_POST_ID}) REFERENCES ${DatabaseContract.PostEntry.TABLE_NAME}(${DatabaseContract.PostEntry.COLUMN_POST_ID}),
                FOREIGN KEY (${DatabaseContract.LikeEntry.COLUMN_USER_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID})
            )
        """.trimIndent()
        db.execSQL(createLikeTable)

        // Create Friendship table
        val createFriendshipTable = """
            CREATE TABLE ${DatabaseContract.FriendshipEntry.TABLE_NAME} (
                ${DatabaseContract.FriendshipEntry.COLUMN_FRIENDSHIP_ID} INTEGER PRIMARY KEY,
                ${DatabaseContract.FriendshipEntry.COLUMN_USER1_ID} INTEGER,
                ${DatabaseContract.FriendshipEntry.COLUMN_USER2_ID} INTEGER,
                ${DatabaseContract.FriendshipEntry.COLUMN_STATUS} TEXT,
                ${DatabaseContract.FriendshipEntry.COLUMN_TIMESTAMP} DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (${DatabaseContract.FriendshipEntry.COLUMN_USER1_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID}),
                FOREIGN KEY (${DatabaseContract.FriendshipEntry.COLUMN_USER2_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID})
            )
        """.trimIndent()
        db.execSQL(createFriendshipTable)

        // Create Notification table
        val createNotificationTable = """
            CREATE TABLE ${DatabaseContract.NotificationEntry.TABLE_NAME} (
                ${DatabaseContract.NotificationEntry.COLUMN_NOTIFICATION_ID} INTEGER PRIMARY KEY,
                ${DatabaseContract.NotificationEntry.COLUMN_USER_ID} INTEGER,
                ${DatabaseContract.NotificationEntry.COLUMN_TYPE} TEXT,
                ${DatabaseContract.NotificationEntry.COLUMN_SOURCE_ID} INTEGER,
                ${DatabaseContract.NotificationEntry.COLUMN_TIMESTAMP} DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (${DatabaseContract.NotificationEntry.COLUMN_USER_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID}),
                FOREIGN KEY (${DatabaseContract.NotificationEntry.COLUMN_SOURCE_ID}) REFERENCES ${DatabaseContract.UserEntry.TABLE_NAME}(${DatabaseContract.UserEntry.COLUMN_USER_ID})
            )
        """.trimIndent()
        db.execSQL(createNotificationTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop old tables and recreate them (for simplicity, this deletes all data)
        val dropUserTable = "DROP TABLE IF EXISTS ${DatabaseContract.UserEntry.TABLE_NAME}"
        db.execSQL(dropUserTable)

        val dropPostTable = "DROP TABLE IF EXISTS ${DatabaseContract.PostEntry.TABLE_NAME}"
        db.execSQL(dropPostTable)

        val dropCommentTable = "DROP TABLE IF EXISTS ${DatabaseContract.CommentEntry.TABLE_NAME}"
        db.execSQL(dropCommentTable)

        val dropLikeTable = "DROP TABLE IF EXISTS ${DatabaseContract.LikeEntry.TABLE_NAME}"
        db.execSQL(dropLikeTable)

        val dropFriendshipTable = "DROP TABLE IF EXISTS ${DatabaseContract.FriendshipEntry.TABLE_NAME}"
        db.execSQL(dropFriendshipTable)

        val dropNotificationTable = "DROP TABLE IF EXISTS ${DatabaseContract.NotificationEntry.TABLE_NAME}"
        db.execSQL(dropNotificationTable)

        onCreate(db)
    }

    fun insertUser(user: User) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UserEntry.COLUMN_USER_ID, user.user_id)
            put(DatabaseContract.UserEntry.COLUMN_USERNAME, user.username)
            put(DatabaseContract.UserEntry.COLUMN_EMAIL, user.email)
            // Add other fields if needed
        }
        db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun getUserByEmail(email: String): User? {
        val db = readableDatabase
        val selection = "${DatabaseContract.UserEntry.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(
            DatabaseContract.UserEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                cursor.getLong(cursor.getColumnIndex(DatabaseContract.UserEntry.COLUMN_USER_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseContract.UserEntry.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(DatabaseContract.UserEntry.COLUMN_EMAIL))
                // Retrieve other fields similarly
            )
        }
        cursor.close()
        db.close()
        return user
    }

    companion object {
        const val DATABASE_NAME = "social_media.db"
        const val DATABASE_VERSION = 1
    }
}
