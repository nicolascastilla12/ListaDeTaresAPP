package com.example.listadecomprasapp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ItemRepository) : ViewModel() {

    // Expone la lista de items como un StateFlow
    val allItems: StateFlow<List<Item>> = repository.allItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Mantiene el flow activo 5s
            initialValue = emptyList() // Valor inicial
        )

    // Funciones que la UI llamará

    fun addItem(name: String) {
        if (name.isNotBlank()) {
            viewModelScope.launch {
                repository.insert(Item(name = name))
            }
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}

// Factory: necesario para poder pasar el 'repository' al ViewModel
class ShoppingViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}