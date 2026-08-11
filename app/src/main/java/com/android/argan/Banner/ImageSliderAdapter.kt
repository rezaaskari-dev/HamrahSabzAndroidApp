package com.android.argan.Banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.argan.Home.HomeFragment
import com.android.argan.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

open class ImageSliderAdapter(
    private val imageResourceIds: List<Int>,
    fragment: HomeFragment,
) : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(holder.itemView.context)
            .load(imageResourceIds[position])
            .apply(requestOptions)
            .into(holder.imageView)


    }

    override fun getItemCount(): Int {
        return imageResourceIds.size
    }

    class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.view_pager)
    }
}
