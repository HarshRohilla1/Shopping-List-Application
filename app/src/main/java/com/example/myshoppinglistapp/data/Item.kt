package com.example.myshoppinglistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    @ColumnInfo(name = "item-name")
    val name:String ="",
    @ColumnInfo(name = "item-quantity")
    val quantity:Int = 0
)
