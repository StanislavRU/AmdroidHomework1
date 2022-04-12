package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val content: String,
    val published: String,
    val author: String,
    val video: String? = null,
    val likedByMe: Boolean,
    val valueLiked: Long = 0L,
    val valueRepost: Long = 0L,
    val valueViews: Long = 0L
) {
    fun toDto() = Post(id, content, published, author, video, likedByMe, valueLiked, valueRepost, valueViews)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.content, dto.published, dto.author, dto.video, dto.likedByMe,
                dto.valueLiked, dto.valueRepost, dto.valueViews)
    }
}
