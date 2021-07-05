package es.jccarrillo.simplelistkotlin.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import es.jccarrillo.simplelistkotlin.domain.ioc.DaggerAppTestComponent
import es.jccarrillo.simplelistkotlin.domain.ioc.ProviderModule
import es.jccarrillo.simplelistkotlin.util.CoroutineTestRule
import es.jccarrillo.simplelistkotlin.util.checkValues
import es.jccarrillo.simplelistkotlin.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val appComponent =
            DaggerAppTestComponent.builder().providerModule(ProviderModule(appContext)).build()
        val itemsRepository = appComponent.itemsRepository
        itemsRepository.clearLocalItems()

        viewModel = appComponent.mainViewModel
    }

    @Test
    fun testItems() = runBlockingTest {
        setUp()
        val items10 = viewModel.items().getOrAwaitValue()
        Assert.assertTrue("Items length ${items10.size}", items10.size == 10)
    }

    @Test
    fun testLoadMoreItems() = runBlockingTest {
        setUp()
        val items10 = viewModel.items().getOrAwaitValue()
        Assert.assertTrue("Items length ${items10.size}, should be 10", items10.size == 10)
        viewModel.loadMoreItems()
        val items20 = viewModel.items().getOrAwaitValue(discardFirstData = true)
        Assert.assertTrue("Items length ${items20.size}, should be 20", items20.size == 20)
    }

    @Test
    fun testReloadItems() = runBlockingTest {
        setUp()
        val items10 = viewModel.items().getOrAwaitValue()
        Assert.assertTrue("Items length ${items10.size}, should be 10", items10.size == 10)
        viewModel.reload()
        val itemsReloaded = viewModel.items().getOrAwaitValue(discardFirstData = true)
        Assert.assertTrue(
            "Items length ${itemsReloaded.size}, should be 10",
            itemsReloaded.size == 10
        )
    }

    @Test
    fun testState() = runBlockingTest {
        setUp()
        Assert.assertEquals(
            "Should be not init",
            MainViewModel.State.NOT_INIT,
            viewModel.state().getOrAwaitValue()
        )

        viewModel.loadMoreItems()

        viewModel.state().checkValues(
            values = listOf(
                MainViewModel.State.LOADING,
                MainViewModel.State.LOADED_MORE_DATA
            )
        )

    }
}