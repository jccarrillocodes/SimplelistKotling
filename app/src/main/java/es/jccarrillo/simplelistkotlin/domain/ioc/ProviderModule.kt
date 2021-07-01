package es.jccarrillo.simplelistkotlin.domain.ioc

import android.content.Context
import dagger.Module
import dagger.Provides
import es.jccarrillo.simplelistkotlin.data.local.provider.ItemsDAO
import es.jccarrillo.simplelistkotlin.data.local.provider.ItemsDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ProviderModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideLocalProvider(dao: ItemsDAO): es.jccarrillo.simplelistkotlin.data.local.provider.ItemsProvider {
        return es.jccarrillo.simplelistkotlin.data.local.provider.ItemsProviderImpl(dao)
    }

    @Provides
    @Singleton
    fun provideItemsDAO(database: ItemsDatabase): ItemsDAO {
        return database.itemsDao()
    }

    @Provides
    @Singleton
    fun provideItemsDatabase(): ItemsDatabase {
        return ItemsDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRemoteProvider(retrofit: Retrofit): es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProvider {
        return es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProviderImpl.build(retrofit)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jccarrillo.es/sandbox/simplelistkotlin/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}