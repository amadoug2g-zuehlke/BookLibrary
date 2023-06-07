package com.example.booklibrary.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Volume

class BookVolumeAdapter(private var action: BookAction) :
    RecyclerView.Adapter<BookVolumeViewHolder>() {

    private var volumeList = emptyList<Volume>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVolumeViewHolder {
        return BookVolumeViewHolder.from(parent)
    }

    override fun getItemCount(): Int = volumeList.size

    override fun onBindViewHolder(holder: BookVolumeViewHolder, position: Int) {
        holder.bind(volumeList[position], action)
    }

    fun setData(newList: List<Volume>) {
        val diffUtil = BookListDiffUtil(volumeList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        volumeList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}