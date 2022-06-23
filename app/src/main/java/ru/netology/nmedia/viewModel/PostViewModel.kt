package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.data.impl.PostInteractionsListener
import ru.netology.nmedia.dto.Post

class PostViewModel:ViewModel(), PostInteractionsListener {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClick(content: String) {
        if (content.isBlank()) return

        val newPost = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today"
        )
        repository.save(newPost)
        currentPost.value = null
    }

    fun onCloseButtonClick() {
        currentPost.value = null
    }


    override fun onShareClicked(post: Post) = repository.share(post.id)
    override fun onLikeClicked(post: Post) = repository.like(post.id)
    override fun onDeleteClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }
}

