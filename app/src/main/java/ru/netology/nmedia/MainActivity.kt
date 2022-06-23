package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editGroup.visibility = View.GONE
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.saveButton.setOnClickListener{
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClick(content)
                clearFocus()
                hideKeyboard()
            }
            binding.editGroup.visibility = View.GONE
        }
        binding.closeButton.setOnClickListener{
            binding.editGroup.visibility = View.GONE
            with(binding.contentEditText) {
                clearFocus()
                hideKeyboard()
                viewModel.currentPost.value = null
            }
        }
        viewModel.currentPost.observe(this){ currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if(content != null) {
                    requestFocus()
                    binding.editGroup.visibility = View.VISIBLE
                }
            }
            binding.contentTextView.text = currentPost?.content

        }
    }
}