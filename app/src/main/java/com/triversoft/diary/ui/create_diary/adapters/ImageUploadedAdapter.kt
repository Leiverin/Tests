package com.triversoft.diary.ui.create_diary.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.triversoft.diary.databinding.ItemListPhotoSelectedBinding
import com.triversoft.diary.databinding.ItemPhotoSelectedBinding
import com.triversoft.diary.util.Constants

class ImageUploadedAdapter: Adapter<ImageUploadedAdapter.ImageUploadViewHolder>(){

    private val paths = mutableListOf<String>()
    var onClickPhoto: (String) -> Unit = {}

    fun submitData(paths: MutableList<String>){
        this.paths.clear()
        this.paths.addAll(paths)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageUploadViewHolder {
        return ImageUploadViewHolder(ItemPhotoSelectedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = paths.size

    override fun onBindViewHolder(holder: ImageUploadViewHolder, position: Int) {
        holder.binds(paths[position])
    }

    inner class ImageUploadViewHolder(private val binding: ItemPhotoSelectedBinding): ViewHolder(binding.root){
        fun binds(path: String){
            binding.path = path
            binding.isDefault = path == Constants.DEFAULT
            binding.viewContainer.setOnClickListener {
                onClickPhoto.invoke(path)
            }
        }
    }

}