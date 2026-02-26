# Mango Android Challenge

Prueba técnica para Mango. El objetivo era construir una aplicación Android en Kotlin que consume una API pública y permite gestionar productos y favoritos.

---

## Qué hace la app

- **Productos** — carga una lista de productos desde la red y los muestra en una grilla. Incluye buscador por nombre y pull-to-refresh para actualizar manualmente.
- **Favoritos** — permite marcar y desmarcar cualquier producto como favorito. Los favoritos se persisten en una base de datos Room local, por lo que sobreviven al cierre y reapertura de la app.
- **Perfil** — muestra los datos del usuario obtenidos de la API, incluyendo cuántos productos tiene guardados como favoritos en ese momento.

API utilizada: [https://fakestoreapi.com](https://fakestoreapi.com)

---

## Arquitectura

El proyecto sigue **Clean Architecture** en una estructura **multi-módulo**:

```
:app
:core:domain      — modelos, interfaces de repositorio y casos de uso (Kotlin puro, sin dependencias Android)
:core:data        — implementaciones con Retrofit y Room, datasources, mappers
:core:ui          — componentes Compose compartidos entre features
:core:testing     — utilidades de test compartidas (MainDispatcherRule, TestFixtures)
:feature:products
:feature:favorites
:feature:profile
```

Las reglas de dependencia son estrictas: `:core:domain` no depende de nada. Los módulos de feature dependen de `:core:domain` y `:core:ui`, pero nunca entre ellos. Todo el wiring de dependencias ocurre en `:core:data` y `:app` a través de Hilt.

El flujo de datos sigue la dirección:

```
Composable → ViewModel → UseCase → Repository → DataSource (Remote / Local)
```

Los ViewModels exponen el estado con `StateFlow` usando un `sealed interface` (`UiState`) con tres variantes: `Loading`, `Error` y `Success`. Todas las pantallas usan el composable compartido `StateWrapper` para manejar estos tres estados de forma consistente.

---

## Stack técnico

| Qué | Cómo |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Patrón | Clean Architecture + MVVM |
| Inyección de dependencias | Hilt |
| Red | Retrofit + Gson + OkHttp (logging interceptor solo en debug) |
| Base de datos local | Room |
| Asincronía | Coroutines + Flow |
| Imágenes | Coil |
| Navegación | Navigation Compose con bottom navigation bar |

---

## Tests

### Unit tests
Cubren todos los ViewModels y `ProductRepositoryImpl`. Usan:
- **JUnit 4** como runner
- **MockK** para mocks
- **Turbine** para testear `Flow`
- **MainDispatcherRule** (del módulo `:core:testing`) para reemplazar el dispatcher principal en tests
- **TestFixtures** para datos de prueba reutilizables

### Instrumented tests *(bonus)*
- **`FavoriteProductDaoTest`** — cubre el CRUD completo del DAO usando una base de datos Room en memoria
- **`NavigationTest`** — verifica la navegación entre las tres pestañas del bottom nav usando Hilt + Compose Test

---

## Strings localizados

Todos los textos de la interfaz están externalizados en archivos `strings.xml` por módulo, sin hardcodear strings en los composables. Cada módulo de feature tiene sus propios recursos de strings:

- `:feature:products` — título de pantalla, placeholder del buscador, estado vacío
- `:feature:favorites` — título de pantalla, estado vacío
- `:feature:profile` — título de pantalla, contador de favoritos, etiquetas de contacto
- `:app` — etiquetas del bottom nav

---

## Cómo ejecutar

1. Clonar el repositorio
2. Abrir en Android Studio
3. Ejecutar en un dispositivo o emulador con API 24+

No se necesita ninguna clave de API ni configuración adicional.
