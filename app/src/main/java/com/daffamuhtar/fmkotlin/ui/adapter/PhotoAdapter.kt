package com.daffamuhtar.fmkotlin.ui.adapter

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyLog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daffamuhtar.fmkotlin.R
import com.daffamuhtar.fmkotlin.databinding.ItemPhotoBinding
import com.daffamuhtar.fmkotlin.model.Photo
import com.daffamuhtar.fmkotlin.util.PhotoHelper
import java.util.*

class PhotoAdapter() : RecyclerView.Adapter<PhotoAdapter.LaporanViewHolder>() {

    private var items = listOf<Photo>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(allItems: List<Photo>) {
        allItems.let {
            this.items = it
            notifyDataSetChanged()
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: Photo, position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class LaporanViewHolder(private val view: ItemPhotoBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: Photo) {
            with(view) {

                PhotoHelper.setPhotoOrVideo(view.root.context,item.file, ivPhoto)


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanViewHolder {
        val view = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LaporanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaporanViewHolder, position: Int) {
        holder.bind(items[position])

//        if (!items[position].isEditable) {
//            holder.itemView.setOnClickListener {
//                onItemClickCallback.onItemClicked(items[holder.adapterPosition], position)
//            }
//        } else {
//            holder.itemView.isClickable = false
//        }
    }

    override fun getItemCount(): Int = items.size
}