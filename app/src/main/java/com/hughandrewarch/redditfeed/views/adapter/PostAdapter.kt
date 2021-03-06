package com.hughandrewarch.redditfeed.views.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hughandrewarch.redditfeed.R
import com.hughandrewarch.redditfeed.data.model.Post


class PostViewAdapter(
    var posts: List<Post>,
    val listener: (Post) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var userTemplate: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)

        userTemplate = parent.context.getString(R.string.username)
        return PostViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = posts[position]

        val viewHolder = holder as PostViewHolder
        viewHolder.title.text = post.title
        viewHolder.author.text = userTemplate.replace(":user", post.author)

        Glide.with(viewHolder.itemView)
            .load(post.thumbnail)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewHolder.thumbnail.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(viewHolder.thumbnail)

        viewHolder.itemView.setOnClickListener{listener(post)}
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun update(updated: List<Post>) {
        posts = updated
        notifyDataSetChanged()
    }

    internal inner class PostViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var author: TextView = itemView.findViewById(R.id.author)
        var thumbnail = itemView.findViewById<android.widget.ImageView>(R.id.thumbnail)
    }
}