package ru.netology.nmedia.data.impl

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post
import java.util.*
import kotlin.properties.Delegates
import kotlin.reflect.KFunction1
import kotlin.collections.List as List1

internal class PostsAdapter(
    private val onLikeClicked: KFunction1<ru.netology.nmedia.dto.Post, Unit>,
    private val onShareClicked: KFunction1<ru.netology.nmedia.dto.Post, Unit>
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            postAuthorName.text = post.author
            postText.text = post.content
            postDate.text = post.published
            postFavoriteText.text = countNumbers(post.likes)
            postShareText.text = countNumbers(post.shares)
            postFavoriteButton.setImageResource(getLikeIconResId(post.likedByMe))
            postFavoriteButton.setOnClickListener { onLikeClicked(post) }
            postShareButton.setOnClickListener { onShareClicked(post) }
        }

        private fun countNumbers(likes: Int): String {
            return when (likes) {
                in 1..999 -> "$likes"
                in 1000..1099 -> "${likes / 1000}K"
                in 1100..9999 -> "${likes / 1000}.${likes / 100 % 10}K"
                in 10000..999999 -> "${likes / 1000}K"
                in 1_000_000..1_099_999 -> "${likes / 1000000}M"
                in 1_000_000..9_999_999 -> "${likes / 1000_000}.${likes / 1000_000 % 10}M"
                in 10_000_000..99_999_999 -> "${likes / 1000000}M"
                else -> ""
            }
        }
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_favorite_like else R.drawable.ic_like_24dp

    fun ActivityMainBinding.render(list: List<Post>?) {

    }


    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

    }
