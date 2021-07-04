package es.jccarrillo.simplelistkotlin.domain.ioc

import dagger.Component
import es.jccarrillo.simplelistkotlin.ui.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ProviderModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)


}