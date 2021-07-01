package es.jccarrillo.simplelistkotlin.domain.ioc

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProviderModule::class, RepositoryModule::class])
interface AppComponent {
}