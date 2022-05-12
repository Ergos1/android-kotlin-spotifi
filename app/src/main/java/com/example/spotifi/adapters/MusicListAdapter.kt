package com.example.spotifi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.remote.models.MusicApi
import com.example.spotifi.R
import com.squareup.picasso.Picasso

class MusicListAdapter(private val selectListener: MusicListSelectListener) :
    ListAdapter<MusicApi, MusicListAdapter.ViewHolder>(DIFF_CONFIG) {
    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<MusicApi>() {
            override fun areContentsTheSame(oldItem: MusicApi, newItem: MusicApi): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: MusicApi, newItem: MusicApi): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var music = getItem(position)
        holder.bind(music)
        holder.itemView.setOnClickListener {
            val musics: ArrayList<MusicApi> = ArrayList()
            for (i in 0 until itemCount) {
                musics.add(getItem(i))
            }
            selectListener.getMusic(music, musics)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.title)
        var itemArtist: TextView = itemView.findViewById(R.id.artist)
        var itemImage: ImageView = itemView.findViewById(R.id.preview_image)
        fun bind(music: MusicApi) {
            itemTitle.text = music.title
            itemArtist.text = music.artist.title
            Picasso.get().load(music.preview_url).into(itemImage)
        }
    }

}

interface MusicListSelectListener {
    fun getMusic(music: MusicApi, musics: ArrayList<MusicApi>)
}