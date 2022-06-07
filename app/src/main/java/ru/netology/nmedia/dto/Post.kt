package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var countLikes: UInt,
    var countShare: UInt,
    var countGlaz: UInt
) {
    fun uIntToString(n:UInt):String {
        val res = when {
            n < 1000u -> n.toString()
            n < 10000u -> "${n/1000u}.${n/100u%10u}K"
            n < 1000000u -> "${n/1000u}K"
            n < 10000000u -> "${n/1000000u}.${n/100000u%10u}M"
            n < 100000000u -> "${n/1000000u}M"
            else -> "очМного"
        }
        return res
    }
}