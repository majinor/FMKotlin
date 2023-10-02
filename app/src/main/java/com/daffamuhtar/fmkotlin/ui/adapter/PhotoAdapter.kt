package com.daffamuhtar.fmkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffamuhtar.fmkotlin.databinding.ItemPhotoBinding
import com.daffamuhtar.fmkotlin.data.Photo
import com.daffamuhtar.fmkotlin.util.PhotoHelper

class PhotoAdapter() : RecyclerView.Adapter<PhotoAdapter.LaporanViewHolder>() {

    private var items = listOf<Photo>()
    private var maxItem = 0
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItems(allItems: List<Photo>, maxItem: Int) {
        allItems.let {
            this.items = it
            this.maxItem = maxItem
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
        fun bind(item: Photo, position: Int) {
            with(view) {

                PhotoHelper.setPhotoOrVideo(view.root.context, item.file, ivPhoto)


                btnAddPhoto.visibility = if (position == items.size && position <= maxItem) View.VISIBLE else View.GONE

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
        holder.bind(items[position],position)

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