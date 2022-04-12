package com.fawaz.notesapp.data.room

import androidx.room.TypeConverter
import com.fawaz.notesapp.data.entity.Priority

class Converter {
    //Ini konvert sebuah enum class menjadi string
    //fungsi ini di panggil ketika get sebuah database
    @TypeConverter
    fun fromPriority(priority: Priority) : String{
        return priority.name
    }

    //ini adalah sebuah converter yang mengonvert string ke enum Priority
    //fungsi ini di panggil ketika add dan update sebuah data kedalam database
    @TypeConverter
    fun toPriotity(priority: String) : Priority {
        return Priority.valueOf(priority)
    }
}