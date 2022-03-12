package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnActionListener {
    fun onEditClicked(post: Post) = Unit
    fun onRemoveClicked(post: Post) = Unit
    fun onLikeClicked(post: Post) = Unit
    fun onShareClicked(post: Post) = Unit
    fun onViewsClicked(post: Post) = Unit
    fun onVideoPlayClicked(post: Post) = Unit
}

class PostAdapter(
    private val actionListener: OnActionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            actionListener
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
    private val actionListener: OnActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            avatar.setImageResource(R.drawable.ic_netology_48dp)
            content.text = post.content
            authorName.text = post.author
            published.text = post.published
            repost.text = getValueToText(post.valueRepost)
            views.text = getValueToText(post.valueViews)
            likes.isChecked = post.likedByMe
            likes.text = getValueToText(post.valueLiked)
            if (post.video != null) {
                video.visibility = View.VISIBLE
                videoPlay.visibility = View.VISIBLE
            } else {
                video.visibility = View.GONE
                videoPlay.visibility = View.GONE
            }


            likes.setOnClickListener {
                actionListener.onLikeClicked(post)
            }

            repost.setOnClickListener {
                actionListener.onShareClicked(post)
            }

            views.setOnClickListener {
                actionListener.onViewsClicked(post)
            }

            videoPlay.setOnClickListener {
                actionListener.onVideoPlayClicked(post)
            }

            video.setOnClickListener {
                actionListener.onVideoPlayClicked(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.menu_remove -> {
                                actionListener.onRemoveClicked(post)
                                true
                            }
                            R.id.menu_edit -> {
                                actionListener.onEditClicked(post)
                                true
                            }
                            else -> false
                        }
                    }
                    show()
                }
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