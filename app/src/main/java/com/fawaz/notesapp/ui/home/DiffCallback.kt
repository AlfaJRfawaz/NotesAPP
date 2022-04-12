package com.fawaz.notesapp.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.fawaz.notesapp.data.entity.Notes

class DiffCallback(private val oldList: List<Notes>, private val newList: List<Notes>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // all the reference all the same
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldList[oldItemPosition]
        val newData = newList[newItemPosition]
        // are the content are completely the same
        return  oldData.id == newData.id &&
                oldData.date == newData.date &&
                oldData.description == newData.description &&
                oldData.priority == newData.priority &&
                oldData.title == newData.title
    }

}