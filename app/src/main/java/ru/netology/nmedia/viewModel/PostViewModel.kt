package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data   //data был impl

    fun onLikeClicked() = repository.like()

    fun onShareClicked() = repository.share()
}