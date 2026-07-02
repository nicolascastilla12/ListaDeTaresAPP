package com.example.listadecomprasapp

import android.app.Application

class ShoppingApplication : Application() {
    // Usamos 'by lazy' para que la base de datos y el repositorio
    // se creen solo cuando se necesiten por primera vez.
    val database by lazy { AppDatabase.Companion.getDatabase(this) }
    val repository by lazy { ItemRepository(database.itemDao()) }
}