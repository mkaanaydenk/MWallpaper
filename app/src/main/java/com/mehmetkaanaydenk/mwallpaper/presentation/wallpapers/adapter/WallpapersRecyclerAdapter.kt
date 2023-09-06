package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.mehmetkaanaydenk.mwallpaper.databinding.WallpapersRecyclerRowBinding
import com.mehmetkaanaydenk.mwallpaper.domain.model.Wallpaper
import javax.inject.Inject

class WallpapersRecyclerAdapter @Inject constructor(val glide : RequestManager): RecyclerView.Adapter<WallpapersRecyclerAdapter.WallpapersHolder>() {
    class WallpapersHolder(val binding : WallpapersRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Wallpaper>(){
        override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var wallpaperList : List<Wallpaper>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersHolder {
        val binding = WallpapersRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WallpapersHolder(binding)
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    override fun onBindViewHolder(holder: WallpapersHolder, position: Int) {
        glide.load(wallpaperList[position].previewURL)
            .into(holder.binding.wallpaperImageView)
    }


}