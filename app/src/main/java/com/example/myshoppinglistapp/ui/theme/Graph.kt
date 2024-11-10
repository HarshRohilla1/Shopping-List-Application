package com.example.myshoppinglistapp
import android.content.Context
import androidx.room.Room
import com.example.myshoppinglistapp.data.ItemDatabase
import com.example.myshoppinglistapp.data.ItemRepository

object Graph {
    lateinit var database: ItemDatabase

    val itemRepository by lazy {
        ItemRepository(itemDao = database.ItemDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, ItemDatabase::class.java, "item_database.db").build()
    }
}