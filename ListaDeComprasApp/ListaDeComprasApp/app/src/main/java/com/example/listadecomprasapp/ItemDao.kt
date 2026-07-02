package com.example.listadecomprasapp
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    // Usamos Flow para que la UI se actualice automáticamente
    // Ordenamos por "comprado" y luego por "nombre"
    @Query("SELECT * FROM shopping_items ORDER BY isPurchased ASC, name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)
}