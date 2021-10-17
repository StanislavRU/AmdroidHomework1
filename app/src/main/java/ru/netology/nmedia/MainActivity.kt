package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            { post -> viewModel.likeById(post.id) },
            { post -> viewModel.shareById(post.id) },
            { post -> viewModel.viewById(post.id) }
        )
        binding.posts.adapter = adapter
        viewModel.data.observe(this, adapter::submitList)
    }
}