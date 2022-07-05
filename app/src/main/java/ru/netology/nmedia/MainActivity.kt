package ru.netology.nmedia


import android.content.Intent
import android.net.Uri

import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClick(postContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch("")
        }

        viewModel.editPostContentScreenEvent.observe(this) { initialContent ->
            postContentActivityLauncher.launch(initialContent)
        }

        viewModel.playVideo.observe(this) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}