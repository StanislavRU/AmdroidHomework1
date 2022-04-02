package ru.netology.nmedia.dao.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {

    override fun getAll() = db.query(
        PostTable.NAME,
        PostTable.ALL_COLUMNS,
        null,
        null,
        null,
        null,
        "${PostTable.Column.ID.columnName} DESC"
    ).use { cursor: Cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toModel()
        }
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
//            TODO: remove hardcoded values
            put(PostTable.Column.AUTHOR.columnName, "Нетология. Университет интернет-профессий будущего")
            put(PostTable.Column.CONTENT.columnName, post.content)
            put(PostTable.Column.PUBLISHER.columnName, "Сейчас")
        }
        val id = if (post.id != 0L) {
            db.update(
                PostTable.NAME,
                values,
                "${PostTable.Column.ID.columnName} = ?",
            arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostTable.NAME, null, values)
        }
        db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return it.toModel()
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
            UPDATE posts SET
                valueLiked = valueLiked + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
            WHERE id = ?;
                """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun shareById(id: Long) {
        db.execSQL(
            """
            UPDATE posts SET
                valueRepost = valueRepost + 1
            WHERE id = ?;
                """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun viewsById(id: Long) {
        db.execSQL(
            """
            UPDATE posts SET
                valueViews = valueViews + 1
            WHERE id = ?;
                """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

    override fun playVideoById(id: Long) {
        TODO("Not yet implemented")
    }

    private fun Cursor.toModel() = Post(
        id = getLong(
            getColumnIndexOrThrow(PostTable.Column.ID.columnName)
        ),
        author = getString(
            getColumnIndexOrThrow(PostTable.Column.AUTHOR.columnName)
        ),
        content = getString(
            getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)
        ),
        likedByMe = getInt(
            getColumnIndexOrThrow(PostTable.Column.LIKE_BY_ME.columnName)
        ) != 0,
        valueLiked = getLong(
            getColumnIndexOrThrow(PostTable.Column.VALUE_LIKES.columnName)
        ),
        valueRepost = getLong(
            getColumnIndexOrThrow(PostTable.Column.VALUE_REPOST.columnName)
        ),
        valueViews = getLong(
            getColumnIndexOrThrow(PostTable.Column.VALUE_VIEWS.columnName)
        ),
        video = getString(
            getColumnIndexOrThrow(PostTable.Column.VIDEO.columnName)
        ),
    )
}