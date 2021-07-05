package es.jccarrillo.simplelistkotlin

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import es.jccarrillo.simplelistkotlin.domain.ioc.AppComponent
import es.jccarrillo.simplelistkotlin.domain.ioc.DaggerAppComponent
import es.jccarrillo.simplelistkotlin.domain.ioc.ProviderModule

class App : MultiDexApplication() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .providerModule(ProviderModule(this))
            .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}