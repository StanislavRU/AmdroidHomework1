package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val key = "posts"
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var postId = 1L
    private var defaultPosts = emptyList<Post>()
    override val data = MutableLiveData(defaultPosts)

    init {
        prefs.getString(key, null)?.let {
            defaultPosts = gson.fromJson(it, type)
            data.value = defaultPosts
        }
    }

    override fun likeById(id: Long) {
        val currentPosts: List<Post> = data.value ?: return
        val result = currentPosts.map {
            if (it.id == id && it.likedByMe) it.copy(
                likedByMe = !it.likedByMe,
                valueLiked = --it.valueLiked
            ) else if (it.id == id && !it.likedByMe) it.copy(
                likedByMe = !it.likedByMe,
                valueLiked = ++it.valueLiked
            ) else it
        }
        data.value = result
        sync()
    }

    override fun shareById(id: Long) {
        val currentPosts: List<Post> = data.value ?: return
        val result = currentPosts.map {
            if (it.id == id) it.copy(
                valueRepost = it.valueRepost + 1
            ) else it
        }
        data.value = result
        sync()
    }

    override fun viewsById(id: Long) {
        val currentPosts: List<Post> = data.value ?: return
        val result = currentPosts.map {
            if (it.id == id) it.copy(
                valueViews = it.valueViews + 1
            ) else it
        }
        data.value = result
        sync()
    }

    override fun removeById(id: Long) {
        data.value = data.value?.filter {it.id != id}
        sync()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            val newPost = post.copy(id = postId++)
            data.value = listOf(newPost) + data.value.orEmpty()
            sync()
            return
        }
        data.value = data.value?.map {
            if (it.id == post.id) {
                it.copy(content = post.content, id = postId++)
            } else {
                it
            }
        }
        sync()
    }

    override fun playVideoById(id: Long) {
        TODO("Not yet implemented")
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, gson.toJson(data.value))
            apply()
        }
    }
}