package es.jccarrillo.simplelistkotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import es.jccarrillo.simplelistkotlin.App
import es.jccarrillo.simplelistkotlin.R
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.inject(this)

        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        viewModel.doSomething()
    }
}