package ru.netology.nmedia

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnActionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val newPostLauncher = registerForActivityResult(NewPostActivityContract()) { text ->
            text ?: return@registerForActivityResult
            viewModel.changeContent(text.toString())
            viewModel.save()
        }
        val editPostLauncher = registerForActivityResult(EditPostActivityContract()) { text ->
            text ?: return@registerForActivityResult
            viewModel.changeContent(text.toString())
            viewModel.save()
        }
        val adapter = PostAdapter(

            object : OnActionListener {

                override fun onEditClicked(post: Post) {
                    viewModel.edit(post)
                    editPostLauncher.launch(post.content)
                }

                override fun onRemoveClicked(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onLikeClicked(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClicked(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val chooser = Intent.createChooser(intent, null)
                    startActivity(chooser)
                }

                override fun onViewsClicked(post: Post) {
                    viewModel.viewById(post.id)
                }

                override fun onVideoPlayClicked(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
            }
        )

        binding.posts.adapter = adapter
        viewModel.data.observe(this, adapter::submitList)

        binding.newPost.setOnClickListener {
            newPostLauncher.launch()
        }
    }
}
