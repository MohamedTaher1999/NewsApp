package com.example.newsapp.data.db

import androidx.room.TypeConverter
import com.example.newsapp.data.models.Source

class Converters {
    @TypeConverter
    fun fromSorce(source:Source):String=source.name

    @TypeConverter
    fun toSource(name:String)=Source(name,name)
}