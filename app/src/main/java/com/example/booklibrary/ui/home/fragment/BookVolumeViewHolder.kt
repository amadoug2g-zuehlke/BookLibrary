package com.example.booklibrary.ui.home.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booklibrary.R
import com.example.booklibrary.databinding.VolumeCardBinding
import com.example.core.domain.model.Volume

class BookVolumeViewHolder(private val binding: VolumeCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(volume: Volume, action: BookAction) {
        binding.volume = volume
    }

    companion object {
        fun from(parent: ViewGroup): BookVolumeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.volume_card, parent, false)
            val layout = VolumeCardBinding.bind(view)
            return BookVolumeViewHolder(layout)
        }
    }
}