package es.jccarrillo.simplelistkotlin.domain.ioc

import dagger.Module
import dagger.Provides
import es.jccarrillo.simplelistkotlin.data.repository.ItemsRepository
import es.jccarrillo.simplelistkotlin.data.repository.ItemsRepositoryImpl
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideItemsRepository(
        local: es.jccarrillo.simplelistkotlin.data.local.provider.ItemsProvider,
        remote: es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProvider
    ): ItemsRepository {
        return ItemsRepositoryImpl(local, remote)
    }
}
