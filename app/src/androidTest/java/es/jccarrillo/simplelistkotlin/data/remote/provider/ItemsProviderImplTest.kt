package es.jccarrillo.simplelistkotlin.data.remote.provider

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class ItemsProviderImplTest {

    private lateinit var provider: ItemsProvider

    @Before
    fun prepare() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jccarrillo.es/sandbox/simplelistkotlin/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        provider = ItemsProviderImpl.Factory.build(retrofit)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("es.jccarrillo.simplelistkotlin", appContext.packageName)
    }

    @Test
    fun checkIfServerGivesAResponse() {
        val items = provider.getItems(0, 10)
        Assert.assertTrue(items.size > 0)
    }

    @Test
    fun checkIfIdsAreDifferent() {
        val items = provider.getItems(0, 10)
        for (outLoop in 0 until items.size)
            for (inLoop in 0 until items.size) {
                if (outLoop == inLoop)
                    continue

                if (items[outLoop].id == items[inLoop].id)
                    Assert.fail()
            }
    }

    @Test
    fun checkIfItemsAreEmpty() {
        val items = provider.getItems(0, 10)
        items.forEach {
            if (it.name.isEmpty())
                Assert.fail()

            if (it.description.isEmpty())
                Assert.fail()

            if (it.full_name.isEmpty())
                Assert.fail()

            if (it.owner.login.isEmpty())
                Assert.fail()

            if (it.owner.avatarUrl.isEmpty())
                Assert.fail()
        }
    }

}