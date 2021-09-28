package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private companion object {
        val defaultPost = Post(
            id = 1,
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по " +
                    "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и " +
                    "управлению. Мы растём сами и помогаем расти студентам: от новичков до " +
                    "уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в " +
                    "каждом уже есть сила, которая заставляет хотеть больше, целиться выше, " +
                    "бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку " +
                    "перемен → http://netolo.gy/fyb\"\n",
            published = "21 мая в 18:36",
            author = "Нетология. Университет интернет-профессий будущего",
            valueLiked = 125L,
            valueRepost = 999L,
            valueViews = 999_999L
        )
    }

    override val data = MutableLiveData(defaultPost)

    override fun like() {
        val currentPost = data.value ?: return
        if (currentPost.likedByMe) {
            data.value = currentPost.copy(
                likedByMe = !currentPost.likedByMe,
                valueLiked = --currentPost.valueLiked
            )
        } else {
            data.value = currentPost.copy(
                likedByMe = !currentPost.likedByMe,
                valueLiked = ++currentPost.valueLiked
            )
        }
    }

    override fun repost() {
        val currentPost = data.value ?: return
        data.value = currentPost.copy(
            valueRepost = ++currentPost.valueRepost
        )
    }

    override fun views() {
        val currentPost = data.value ?: return
        data.value = currentPost.copy(
            valueViews = ++currentPost.valueViews
        )
    }
}