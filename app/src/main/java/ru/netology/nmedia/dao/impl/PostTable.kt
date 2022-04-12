package ru.netology.nmedia.dao.impl

object PostTable {
    const val NAME = "posts"

    val DDL = """
        CREATE TABLE $NAME (
            ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.AUTHOR.columnName} TEXT NOT NULL,
            ${Column.CONTENT.columnName} TEXT NOT NULL,
            ${Column.PUBLISHER.columnName} TEXT NOT NULL,
            ${Column.LIKE_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
            ${Column.VALUE_LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.VALUE_REPOST.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.VALUE_VIEWS.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.VIDEO.columnName} TEXT
        );
        """.trimIndent()

    val ALL_COLUMNS = Column.values()
        .map(Column::columnName)
        .toTypedArray()

    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHER("publisher"),
        LIKE_BY_ME("likedByMe"),
        VALUE_LIKES("valueLiked"),
        VALUE_REPOST("valueRepost"),
        VALUE_VIEWS("valueViews"),
        VIDEO("video")
    }
}