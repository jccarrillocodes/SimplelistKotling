package es.jccarrillo.simplelistkotlin.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            Toast.makeText(this, "Hay ${it.size} Total", Toast.LENGTH_SHORT).show()
        })

        viewModel.state().observe(this, {
            Log.d("MainActivity", "Estado: $it")
        })

    }
}