package com.example.myshoppinglistapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = false
)
abstract class ItemDatabase {
    abstract fun ItemDao():ItemDao
}