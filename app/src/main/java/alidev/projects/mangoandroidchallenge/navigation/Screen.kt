package alidev.projects.mangoandroidchallenge.navigation

import alidev.projects.mangoandroidchallenge.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val labelRes: Int, val icon: ImageVector) {
    data object Products : Screen("products", R.string.tab_products, Icons.Default.Home)
    data object Favorites : Screen("favorites", R.string.tab_favorites, Icons.Default.Favorite)
    data object Profile : Screen("profile", R.string.tab_profile, Icons.Default.Person)
}
