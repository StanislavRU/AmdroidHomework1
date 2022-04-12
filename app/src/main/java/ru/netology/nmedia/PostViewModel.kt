package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryRoomImpl


private val empty = Post()

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // TODO упрощенный вариант
    private val repository: PostRepository = PostRepositoryRoomImpl(
        AppDb.getInstance(context = application).postDao()
    )

    val data = repository.getAll()
    private val _edited = MutableLiveData(empty)

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun viewsById(id: Long) {
        repository.viewsById(id)
    }

    fun edit(post: Post) {
        _edited.value = post
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun changeContent(content: String) {
        _edited.value = _edited.value?.copy(content = content)
    }

    fun save() {
        _edited.value?.also {
            repository.save(it)
            _edited.value = empty
        }
    }
}