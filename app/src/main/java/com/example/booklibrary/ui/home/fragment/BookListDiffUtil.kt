package com.example.booklibrary.ui.home.fragment

import androidx.recyclerview.widget.DiffUtil
import com.example.core.domain.model.Volume

class BookListDiffUtil(
    private val oldList: List<Volume>,
    private val newList: List<Volume>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].volumeInfo.title == newList[newItemPosition].volumeInfo.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]== newList[newItemPosition]
    }
}