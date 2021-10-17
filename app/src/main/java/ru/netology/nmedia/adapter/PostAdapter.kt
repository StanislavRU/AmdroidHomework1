package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnListener = (post: Post) -> Unit

class PostAdapter(
    private val likeListener: OnListener,
    private val shareListener: OnListener,
    private val viewListener: OnListener
) : ListAdapter<Post, PostViewHolder>(PostDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            likeListener, shareListener, viewListener
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostDiffUtilItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeListener: OnListener,
    private val shareListener: OnListener,
    private val viewListener: OnListener
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
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

            likes.setOnClickListener {
                likeListener(post)
            }

            repost.setOnClickListener {
                shareListener(post)
            }

            views.setOnClickListener {
                viewListener(post)
            }
        }
    }

    private fun getValueToText(value: Long): String {
        if (value < 1_000) {
            return value.toString()
        } else if (value in 1_000..9_999) {
            if ((value / 100) % 10 == 0L) {
                return (value / 1_000).toString() + "K"
            } else {
                return ((value / 100).toDouble() / 10).toString() + "K"
            }
        } else if (value in 10_000..99_999) {
            return (value / 1_000).toString() + "K"
        } else if (value in 10_000..999_999) {
            return (value / 1_000).toString() + "K"
        } else {
            return (value / 1_000_000).toString() + "М"
        }
    }
}