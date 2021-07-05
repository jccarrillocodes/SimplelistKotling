package es.jccarrillo.simplelistkotlin.domain.ioc

import dagger.Component
import es.jccarrillo.simplelistkotlin.data.repository.ItemsRepository
import es.jccarrillo.simplelistkotlin.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ProviderModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface AppTestComponent {

    val itemsRepository: ItemsRepository

    val mainViewModel: MainViewModel
}