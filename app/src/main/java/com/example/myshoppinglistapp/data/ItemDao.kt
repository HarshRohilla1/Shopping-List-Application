package com.example.myshoppinglistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAItem(ItemEntity: Item)

    @Query("select * from `ITEM_TABLE`")
    abstract fun getAllItems(): Flow<List<Item>>

    @Update
    abstract suspend fun updateAItem(ItemEntity:Item)

    @Delete
    abstract suspend fun deleteAItem(ItemEntity: Item)

    @Query("select * from `ITEM_TABLE` where id=:id")
    abstract fun getAItemById(id:Long):Flow<Item>

}