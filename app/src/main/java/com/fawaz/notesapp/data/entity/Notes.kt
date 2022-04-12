package com.fawaz.notesapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// anotasi entity yang digunakan untuk menandakan kalau data class ini adalah skema untuk per 1 row-nya
@Entity(tableName = "note_table")
@Parcelize
data class Notes(
    //Untuk id di dalam table supaya tidak di duplikat
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String,
    var date: String
) : Parcelable