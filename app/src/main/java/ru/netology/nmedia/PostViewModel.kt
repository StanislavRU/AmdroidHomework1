package ru.netology.nmedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

private val empty = Post(videoStatus = false)

class PostViewModel : ViewModel() {
    // TODO упрощенный вариант
    private val repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<List<Post>> = repository.data
    private val _edited = MutableLiveData(empty)
    val edited: LiveData<Post>
        get() = _edited

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun viewById(id: Long) {
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

    fun cancelButton() {
        _edited.value = empty
    }
}