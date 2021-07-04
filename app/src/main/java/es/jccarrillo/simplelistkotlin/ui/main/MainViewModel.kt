package es.jccarrillo.simplelistkotlin.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    fun doSomething() {
        Log.v(MainViewModel::class.java.toString(), "Somehitng")
    }
}