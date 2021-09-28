package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
        with(binding) {
            avatar.setImageResource(R.drawable.ic_netology_48dp)
            content.text = post.content
            authorName.text = post.author
            published.text = post.published
            valueLikes.text = getValueToText(post.valueLiked)
            valueRepost.text = getValueToText(post.valueRepost)
            valueViews.text = getValueToText(post.valueViews)

                likes.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_liked
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
            }
        }
        binding.likes.setOnClickListener() {
            viewModel.like()
        }

        binding.repost.setOnClickListener() {
            viewModel.repost()
        }

        binding.views.setOnClickListener() {
            viewModel.views()
        }
    }

    private fun getValueToText(value: Long): String {
        if (value < 1_000) {
            return value.toString()
        } else if (value in 1_000..9_999) {
            if ((value / 100) % 10 == 0L) {
                return (value / 1000).toString() + "K"
            } else {
                return ((value / 100).toDouble() / 10).toString() + "K"
            }
        } else if (value in 10_000..99_999) {
            return (value / 1_000).toString() + "K"
        } else if (value in 10_000..999_999) {
                return (value / 1000).toString() + "K"
        } else {
                return (value / 1_000_000).toString() + "М"
        }
    }
}