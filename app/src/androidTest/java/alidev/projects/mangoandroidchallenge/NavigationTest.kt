package alidev.projects.mangoandroidchallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appStartsOnProductsScreen() {
        composeTestRule
            .onNodeWithText("Lista de productos")
            .assertIsDisplayed()
    }

    @Test
    fun clickingFavoritosTab_navigatesToFavoritesScreen() {
        // "Favoritos" appears only in the bottom nav on the Products screen
        composeTestRule.onNodeWithText("Favoritos").performClick()

        // Products screen title is no longer shown
        composeTestRule.onNodeWithText("Lista de productos").assertDoesNotExist()
    }

    @Test
    fun clickingPerfilTab_navigatesToProfileScreen() {
        // "Perfil" appears only in the bottom nav on the Products screen
        composeTestRule.onNodeWithText("Perfil").performClick()

        // Products screen title is no longer shown
        composeTestRule.onNodeWithText("Lista de productos").assertDoesNotExist()
    }
}
