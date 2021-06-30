package es.jccarrillo.simplelistkotlin.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import es.jccarrillo.simplelistkotlin.data.local.provider.ItemsDatabase
import es.jccarrillo.simplelistkotlin.util.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ItemsRepositoryImplTest : TestCase() {

    private lateinit var itemsRepository: ItemsRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private fun getLocalItemsProvider(): es.jccarrillo.simplelistkotlin.data.local.provider.ItemsProvider {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val database = ItemsDatabase.getDatabase(appContext)
        return es.jccarrillo.simplelistkotlin.data.local.provider.ItemsProviderImpl(database.itemsDao())
    }

    private fun getRemoteItemsProvider(): es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProvider {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jccarrillo.es/sandbox/simplelistkotlin/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProviderImpl.Factory.build(
            retrofit
        )
    }

    private fun getWrongRemoteItemsProvider(): es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProvider {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jccarrillo.es/sandbox/simplelistkotlin/aaa/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProviderImpl.Factory.build(
            retrofit
        )
    }

    public override fun setUp() {
        super.setUp()

        itemsRepository = ItemsRepositoryImpl(getLocalItemsProvider(), getRemoteItemsProvider())

    }

    public override fun tearDown() {
        itemsRepository.clearLocalItems()
    }

    @Test
    fun testGetItems() {
        val items = itemsRepository.getItems(0, 10)
        Assert.assertTrue(items.size == 10)
    }

    @Test
    fun testIfOfflineWorks() {
        val items = itemsRepository.getItems(0, 10)

        itemsRepository =
            ItemsRepositoryImpl(getLocalItemsProvider(), getWrongRemoteItemsProvider())

        val items2 = itemsRepository.getItems(0, 10)

        Assert.assertEquals(items, items2)
    }

    @Test
    fun testClearLocalItems() {
        itemsRepository.getItems(0, 10)

        itemsRepository =
            ItemsRepositoryImpl(getLocalItemsProvider(), getWrongRemoteItemsProvider())
        itemsRepository.clearLocalItems()

        val items2 = itemsRepository.getItems(0, 10)

        Assert.assertTrue(items2.size == 0)
    }
}