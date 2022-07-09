package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionsListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository

class PostViewModel(
    application:Application
):AndroidViewModel(application), PostInteractionsListener {
    private val repository: PostRepository = FilePostRepository(application)

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClick(content:String){
        if (content.isBlank()) return

        val newPost =currentPost.value?.copy(
            content = content
        )?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today"
        )
        repository.save(newPost)
        currentPost.value = null
    }

    override fun onShareClicked(post:Post) = repository.share(post.id)
    override fun onLikeClicked(post:Post) = repository.like(post.id)
    override fun onDeleteClicked(post:Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) { currentPost.value = post }

}