package com.example.listadecomprasapp
import kotlinx.coroutines.flow.Flow

// El repositorio es la única fuente de verdad para los datos
class ItemRepository(private val itemDao: ItemDao) {

    val allItems: Flow<List<Item>> = itemDao.getAllItems()

    suspend fun insert(item: Item) {
        itemDao.insertItem(item)
    }

    suspend fun update(item: Item) {
        itemDao.updateItem(item)
    }

    suspend fun delete(item: Item) {
        itemDao.deleteItem(item)
    }
}