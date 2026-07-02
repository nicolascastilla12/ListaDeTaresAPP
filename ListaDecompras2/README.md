# Gestor de Gastos Personales (Mis Finanzas)

**Estudiante:** [Tu Nombre Completo Aquí]

## Descripción de la Aplicación
Esta aplicación es una herramienta nativa de Android diseñada para ayudar a los usuarios a llevar un control detallado de sus finanzas personales. Permite registrar ingresos y gastos, proporcionando una interfaz clara y persistencia de datos local mediante una base de datos SQLite.

### Características Principales:
*   **Registro de Transacciones:** Formulario para ingresar descripción (concepto), monto y tipo de movimiento (Ingreso o Gasto).
*   **Visualización Dinámica:** Lista principal utilizando `RecyclerView` para un rendimiento óptimo.
*   **Identificación Visual:** Los ingresos se marcan con color verde y los gastos con color rojo.
*   **Operaciones CRUD Completas:**
    *   **Crear:** Añadir nuevos movimientos.
    *   **Leer:** Listado completo del historial financiero.
    *   **Actualizar:** Posibilidad de editar cualquier registro tocándolo en la lista.
    *   **Eliminar:** Borrado de registros directamente desde la pantalla de edición.

## Requerimientos Técnicos Implementados
*   **Lenguaje:** Java.
*   **Base de Datos:** SQLite con `SQLiteOpenHelper`.
*   **Interfaz de Usuario:** XML Layouts, Material Design Components y RecyclerView con Adapter personalizado.
*   **Arquitectura:** Separación de responsabilidades entre Modelo, Base de Datos, Adaptador y Actividades.

## Capturas de Pantalla
*(Nota: Debes adjuntar tus propias capturas de pantalla aquí después de ejecutar la app)*

1. **Lista de Transacciones:**
![Lista de Transacciones](ruta_a_tu_imagen_lista.png)

2. **Formulario de Registro/Edición:**
![Formulario](ruta_a_tu_imagen_formulario.png)

---
*Proyecto desarrollado para el Seguimiento de Android Studio - Julio 2024.*
