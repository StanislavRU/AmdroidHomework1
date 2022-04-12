package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {
    override val data: LiveData<List<Post>>
        get() = TODO("Not yet implemented")

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            it.toDto()
        }
    }
//
//    private var defaultPosts = emptyList<Post>()
//
//    override val data = MutableLiveData(defaultPosts)
//
//    init {
//        defaultPosts = dao.getAll()
//        data.value = defaultPosts
//    }

    override fun likeById(id: Long) {
        dao.likeById(id)
//        defaultPosts = defaultPosts.map {
//            if (it.id != id) it else it.copy(
//                likedByMe = !it.likedByMe,
//                valueLiked = if (it.likedByMe) it.valueLiked - 1L else it.valueLiked + 1L
//            )
//        }
//        data.value = defaultPosts
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
//        defaultPosts = defaultPosts.map {
//            if (it.id == id) it.copy(
//                valueRepost = it.valueRepost + 1L
//            ) else it
//        }
//        data.value = defaultPosts
    }

    override fun viewsById(id: Long) {
        dao.viewsById(id)
//        defaultPosts = defaultPosts.map {
//            if (it.id == id) it.copy(
//                valueViews = it.valueViews + 1
//            ) else it
//        }
//        data.value = defaultPosts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
//        defaultPosts = defaultPosts.filter {it.id != id}
//        data.value = defaultPosts
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
//        val id = post.id
//        defaultPosts = if (id == 0L) {
//            listOf(saved) + defaultPosts
//        } else {
//            defaultPosts.map {
//                if (it.id != id) it else saved
//            }
//        }
//        data.value = defaultPosts
    }

    override fun playVideoById(id: Long) {
        TODO("Not yet implemented")
    }
}