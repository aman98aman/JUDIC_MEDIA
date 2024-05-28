package com.exmpale.mimi.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.exmpale.mimi.Models.GalleryModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.GalleryItemBinding


class GalleryAdapter(private val list:List<GalleryModel>): RecyclerView.Adapter<GalleryAdapter.MyVewHolder>() {

    inner class MyVewHolder(val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVewHolder {
        val inflate = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyVewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {

        holder.binding.root.setOnClickListener {
            holder.binding.galleryImage.setBackgroundResource(R.drawable.image_select_bg)
        }

        val itemList = list[position]

        Glide
            .with(holder.itemView.context)
            .load(itemList.galleryImage)
            .apply(RequestOptions().encodeQuality(5))
            .into(holder.binding.galleryImage)
    }
}