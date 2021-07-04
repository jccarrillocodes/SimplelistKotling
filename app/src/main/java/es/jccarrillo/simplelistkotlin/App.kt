package es.jccarrillo.simplelistkotlin

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import es.jccarrillo.simplelistkotlin.domain.ioc.AppComponent
import es.jccarrillo.simplelistkotlin.domain.ioc.DaggerAppComponent

class App : MultiDexApplication() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}