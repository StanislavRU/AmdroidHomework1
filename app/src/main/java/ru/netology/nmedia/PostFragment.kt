package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewEditPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.utils.LongArg

class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModel by viewModels(::requireParentFragment)
        val postId: Long? = arguments?.longArg

        viewModel.data.observe(viewLifecycleOwner) {
            val post = viewModel.data.value?.find { it.id == postId }
            if (post == null) {
                findNavController().navigateUp()
            } else {
                with(binding.postContent) {
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
                        viewModel.likeById(post.id)
                    }
                    repost.setOnClickListener {
                        viewModel.shareById(post.id)
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, post.content)
                            type = "text/plain"
                        }
                        val chooser = Intent.createChooser(intent, null)
                        startActivity(chooser)
                    }
                    video.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                        if (intent.resolveActivity(requireContext().packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                    videoPlay.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                        if (intent.resolveActivity(requireContext().packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                    menu.setOnClickListener {
                        PopupMenu(it.context, it).apply {
                            inflate(R.menu.post_menu)
                            setOnMenuItemClickListener { menuItem ->
                                when (menuItem.itemId) {
                                    R.id.menu_remove -> {
                                        viewModel.removeById(post.id)
                                        findNavController().navigateUp()
                                        true
                                    }
                                    R.id.menu_edit -> {
                                        viewModel.edit(post)
                                        findNavController().navigate(R.id.action_postFragment_to_newEditPostFragment,
                                            Bundle().apply { textArg = post.content })
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
        }
        return binding.root
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

    companion object {
        var Bundle.longArg: Long? by LongArg
    }
}