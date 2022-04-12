package com.fawaz.notesapp.utils

import android.view.View
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.fawaz.notesapp.R
import com.fawaz.notesapp.data.entity.Notes
import com.fawaz.notesapp.data.entity.Priority
import com.fawaz.notesapp.ui.home.HomeFragmentDirections
import com.google.android.material.card.MaterialCardView

object BindingAdapter {
    // ini(android:parsePriorityColor) akan di panggil dalam XML
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(cardView: MaterialCardView, priority: Priority){
        when(priority){
            Priority.HIGH->{
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))
            }
            Priority.MEDIUM->{
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
            }
            Priority.LOW->{
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            }
        }
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, currentItem:Notes){
        view.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: Spinner, priority: Priority) {
        when (priority) {
            Priority.HIGH -> {
                view.setSelection(0)
            }
            Priority.MEDIUM -> {
                view.setSelection(1)
            }
            Priority.LOW -> {
                view.setSelection(2)
            }
        }
    }

    // di Binding Adapter kita menjalankan state INVISIBLE & VISIBLE
    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        when(emptyDatabase.value) {
            true -> view.visibility = View.VISIBLE
            else -> view.visibility = View.INVISIBLE
        }
    }
}