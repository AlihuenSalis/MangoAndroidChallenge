package alidev.projects.mangoandroidchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mango.challenge.feature.favorites.FavoritesScreen
import com.mango.challenge.feature.products.ProductsScreen
import com.mango.challenge.feature.profile.ProfileScreen

@Composable
fun MangoNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Products.route) {
        composable(Screen.Products.route) { ProductsScreen() }
        composable(Screen.Favorites.route) { FavoritesScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
