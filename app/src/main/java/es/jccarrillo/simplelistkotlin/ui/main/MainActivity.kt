package es.jccarrillo.simplelistkotlin.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        viewModel.items().observe(this, {
            Log.d("MainActivity", "Hay " + it.size + " Total")
        })

    }
}