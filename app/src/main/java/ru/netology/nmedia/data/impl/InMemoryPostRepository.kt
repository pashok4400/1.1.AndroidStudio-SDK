package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {
    private val posts
        get() = checkNotNull(data.value) {
            "Data value not be null"
        }

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Some random content $index",
                published = "04.14.2022",
                likes = 111
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(likedByMe = !it.likedByMe)
        }
        data.value = posts.map {
            if (it.id == postId) {
                if (it.likedByMe) it.copy(likes = it.likes + 1)
                else it.copy(likes = it.likes - 1)
            } else it
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(shares = it.shares + 10)
        }
    }
}