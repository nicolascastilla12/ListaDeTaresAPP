package com.example.listadecomprasapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        // Volatile asegura que la instancia sea visible para todos los hilos
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Singleton pattern: si ya existe la instancia, la devuelve
            // si no, la crea de forma segura (synchronized)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopping_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}