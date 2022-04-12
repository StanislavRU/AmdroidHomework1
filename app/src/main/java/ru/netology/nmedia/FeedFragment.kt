package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.NewEditPostFragment.Companion.textArg
import ru.netology.nmedia.PostFragment.Companion.longArg
import ru.netology.nmedia.adapter.OnActionListener
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post

class FeedFragment : Fragment(), OnActionListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        val adapter = PostAdapter(

            object : OnActionListener {

                override fun onEditClicked(post: Post) {
                    viewModel.edit(post)
                    findNavController().navigate(R.id.action_feedFragment_to_newEditPostFragment,
                        Bundle().apply { textArg = post.content })
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

                override fun onVideoPlayClicked(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(intent)
                    }
                }

                override fun onPostClicked(post: Post) {
                    viewModel.viewsById(post.id)
                    findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                        Bundle().apply
                        { longArg = post.id })
                }
            }
        )

        binding.posts.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, adapter::submitList)

        binding.newPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newEditPostFragment)
        }
        return binding.root
    }
}
