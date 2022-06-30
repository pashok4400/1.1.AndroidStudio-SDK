package ru.netology.nmedia.data

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes:Int = 0,
    val shares:Int = 0,
    var likedByMe: Boolean = false
)