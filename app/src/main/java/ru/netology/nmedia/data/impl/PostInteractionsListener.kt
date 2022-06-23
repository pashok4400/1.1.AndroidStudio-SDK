package ru.netology.nmedia.data.impl

import ru.netology.nmedia.dto.Post

interface PostInteractionsListener {
    fun onLikeClicked(post:Post)
    fun onShareClicked(post:Post)
    fun onDeleteClicked(post:Post)
    fun onEditClicked(post: Post)
}