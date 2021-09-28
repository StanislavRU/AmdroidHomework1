package ru.netology.nmedia.dto

data class Post (
    val id: Long = 0L,
    val content: String = "",
    val published: String = "",
    val author: String = "",
    val likedByMe: Boolean = false,
    var valueLiked: Long = 0L,
    var valueRepost: Long = 0L,
    var valueViews: Long = 0L
        )


