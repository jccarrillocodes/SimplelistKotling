package es.jccarrillo.simplelistkotlin.data.local.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse
import es.jccarrillo.simplelistkotlin.util.CoroutineTestRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ItemsProviderImplTest : TestCase() {

    lateinit var itemsProvider: ItemsProvider

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    public override fun setUp() {
        super.setUp()

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val database = ItemsDatabase.getDatabase(appContext)
        itemsProvider = ItemsProviderImpl(database.itemsDao())
    }

    public override fun tearDown() = runBlockingTest {
        itemsProvider.clearStorage()
    }

    fun testGetItems() {
        val res = itemsProvider.getItems(0, 10)
        Assert.assertTrue(res.size == 0)
    }

    fun testStoreItems() = runBlockingTest {
        val items = ItemsResponse()
        val item = Item()
        item.id = 1
        item.owner.login = "Test"
        items.add(item)
        itemsProvider.storeItems(items)

        val retrieved = itemsProvider.getItems(0, 10)
        Assert.assertEquals(retrieved[0].id, 1)
        Assert.assertEquals(retrieved[0].owner.login, "Test")
    }

    fun testStoreItemsAndPage() = runBlockingTest {
        val items = ItemsResponse()
        for( i in 1..20) {
            val item = Item()
            item.id = i
            item.owner.login = "Test $i"
            items.add(item)
        }
        itemsProvider.storeItems(items)

        val retrieved = itemsProvider.getItems(1, 10)
        Assert.assertEquals(retrieved[0].id, 11)

        val retrieved2 = itemsProvider.getItems(1, 5)
        Assert.assertEquals(retrieved2[0].id, 6)
    }

    fun testClearStorage() = runBlockingTest {
        val items = ItemsResponse()
        val item = Item()
        item.id = 1
        item.owner.login = "Test"
        items.add(item)
        itemsProvider.storeItems(items)

        itemsProvider.clearStorage()

        val retrieved = itemsProvider.getItems(0, 10)
        Assert.assertEquals(retrieved.size, 0)
    }
}