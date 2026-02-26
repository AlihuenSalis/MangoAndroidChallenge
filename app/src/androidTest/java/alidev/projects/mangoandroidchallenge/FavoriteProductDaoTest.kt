package alidev.projects.mangoandroidchallenge

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mango.challenge.core.data.local.MangoDatabase
import com.mango.challenge.core.data.local.dao.FavoriteProductDao
import com.mango.challenge.core.data.local.entity.FavoriteProductEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteProductDaoTest {

    private lateinit var database: MangoDatabase
    private lateinit var dao: FavoriteProductDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MangoDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoriteProductDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private fun testEntity(id: Int, title: String = "Product $id") = FavoriteProductEntity(
        id = id,
        title = title,
        price = 9.99,
        description = "Description",
        category = "category",
        image = "https://example.com/img.jpg",
        ratingRate = 4.5,
        ratingCount = 100
    )

    @Test
    fun insertFavorite_getAllFavorites_returnsInsertedEntity() = runBlocking {
        val entity = testEntity(id = 1)
        dao.insertFavorite(entity)

        val result = dao.getAllFavorites().first()

        assertEquals(listOf(entity), result)
    }

    @Test
    fun deleteFavorite_entityIsRemovedFromAllFavorites() = runBlocking {
        val entity = testEntity(id = 1)
        dao.insertFavorite(entity)
        dao.deleteFavorite(entity.id)

        val result = dao.getAllFavorites().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun getFavoriteCount_returnsCorrectCountAfterInserts() = runBlocking {
        dao.insertFavorite(testEntity(id = 1))
        dao.insertFavorite(testEntity(id = 2))

        val count = dao.getFavoriteCount().first()

        assertEquals(2, count)
    }

    @Test
    fun getFavoriteIds_returnsIdsOfAllInsertedEntities() = runBlocking {
        dao.insertFavorite(testEntity(id = 1))
        dao.insertFavorite(testEntity(id = 3))

        val ids = dao.getFavoriteIds().first()

        assertEquals(listOf(1, 3), ids.sorted())
    }

    @Test
    fun insertFavorite_withExistingId_replacesExistingEntity() = runBlocking {
        dao.insertFavorite(testEntity(id = 1, title = "Original"))
        dao.insertFavorite(testEntity(id = 1, title = "Updated"))

        val result = dao.getAllFavorites().first()

        assertEquals(1, result.size)
        assertEquals("Updated", result.first().title)
    }
}
