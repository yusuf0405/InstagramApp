package com.example.instagramapp.home_screen.presentation.adapters

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramapp.R
import com.example.instagramapp.databinding.PostItemBinding
import com.example.instagramapp.home_screen.domain.models.Post
import java.util.*


class PostsDiffCallBack(
    private val oldList: List<Post>,
    private val newList: List<Post>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldList[oldItemPosition]
        val newNote = newList[newItemPosition]
        return oldNote == newNote
    }

}


class PostsAdapter(private val actionListener: ItemOnClickListener) :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    var postList: List<Post> = emptyList()
        set(newValue) {
            val diffCallBack = PostsDiffCallBack(oldList = field, newList = newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)

        }

    inner class PostsViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(post: Post) {
            val geocoder = Geocoder(itemView.context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(
                post.latitude,
                post.longitude,
                1)
            val city = addresses[0].locality
            val country = addresses[0].countryName

            binding.apply {
                title.text = post.title
                username.text = post.username
                description.text = post.description
                location.text = "$city , $country"
                Glide.with(itemView.context)
                    .load(post.image.url)
                    .into(image)
            }
            binding.location.setOnClickListener {
                actionListener.showMapApp(latitude = post.latitude, longitude = post.longitude)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        val binding = PostItemBinding.bind(layoutInflater)
        return PostsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int = postList.size

}