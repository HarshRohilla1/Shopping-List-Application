package com.example.myshoppinglistapp.data

import kotlinx.coroutines.flow.Flow

class ItemRepository(private val itemDao: ItemDao) {

    suspend fun addAItem(item:Item){
        itemDao.addAItem(item)
    }

    fun getItem(): Flow<List<Item>> = itemDao.getAllItems()

    fun getAItemById(id:Long):Flow<Item>
    {
        return itemDao.getAItemById(id)
    }

    suspend fun updateAItem(item: Item)
    {
        itemDao.updateAItem(item)
    }
    suspend fun deleteAItem(item:Item)
    {
        itemDao.deleteAItem(item)
    }
}