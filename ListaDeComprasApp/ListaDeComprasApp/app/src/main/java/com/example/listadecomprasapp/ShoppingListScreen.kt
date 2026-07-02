package com.example.listadecomprasapp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(viewModel: ShoppingViewModel) {

    // Observa el StateFlow del ViewModel
    val items by viewModel.allItems.collectAsState()

    // Estados para la UI (agregar y editar)
    var newItemName by remember { mutableStateOf("") }
    var itemToEdit by remember { mutableStateOf<Item?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Lista de Compras") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        // Contenido principal
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // 1. Lista de productos (ocupa el espacio disponible)
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items, key = { it.id }) { item ->
                        ShoppingItemRow(
                            item = item,
                            onItemChecked = {
                                // Modificar: Marcar como comprado
                                viewModel.updateItem(it.copy(isPurchased = !it.isPurchased))
                            },
                            onItemEdit = {
                                // Modificar: Abrir diálogo de edición
                                itemToEdit = it
                            },
                            onItemDelete = {
                                // Eliminar
                                viewModel.deleteItem(it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 2. Formulario para agregar nuevos productos
                AddItemForm(
                    newItemName = newItemName,
                    onNameChange = { newItemName = it },
                    onAddItem = {
                        // Agregar
                        viewModel.addItem(newItemName)
                        newItemName = "" // Limpiar el campo
                    }
                )
            }
        }
    )

    // Diálogo para editar (se muestra solo si itemToEdit no es null)
    if (itemToEdit != null) {
        EditItemDialog(
            item = itemToEdit!!,
            onDismiss = { itemToEdit = null },
            onConfirm = { updatedName ->
                // Modificar: Actualizar nombre
                viewModel.updateItem(itemToEdit!!.copy(name = updatedName))
                itemToEdit = null
            }
        )
    }
}

// Composable para la fila de un item
@Composable
fun ShoppingItemRow(
    item: Item,
    onItemChecked: (Item) -> Unit,
    onItemEdit: (Item) -> Unit,
    onItemDelete: (Item) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox para marcar como comprado
        Checkbox(
            checked = item.isPurchased,
            onCheckedChange = { onItemChecked(item) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Nombre del producto
        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            textDecoration = if (item.isPurchased) TextDecoration.LineThrough else TextDecoration.None,
            color = if (item.isPurchased) Color.Gray else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Botón de Editar
        IconButton(onClick = { onItemEdit(item) }) {
            Icon(Icons.Default.Edit, contentDescription = "Editar Item")
        }

        // Botón de Borrar
        IconButton(onClick = { onItemDelete(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar Item", tint = MaterialTheme.colorScheme.error)
        }
    }
}

// Composable para el formulario de agregar
@Composable
fun AddItemForm(
    newItemName: String,
    onNameChange: (String) -> Unit,
    onAddItem: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = newItemName,
            onValueChange = onNameChange,
            label = { Text("Nuevo producto") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onAddItem() }
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onAddItem, enabled = newItemName.isNotBlank()) {
            Text("Añadir")
        }
    }
}

// Composable para el diálogo de edición
@Composable
fun EditItemDialog(
    item: Item,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var editedName by remember(item) { mutableStateOf(item.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Producto") },
        text = {
            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it },
                label = { Text("Nombre del producto") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (editedName.isNotBlank()) {
                        onConfirm(editedName)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}