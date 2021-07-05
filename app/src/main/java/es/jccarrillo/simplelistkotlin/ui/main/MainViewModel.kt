package es.jccarrillo.simplelistkotlin.ui.main

import android.util.Log
import androidx.lifecycle.*
import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.data.repository.ItemsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: ItemsRepository) : ViewModel() {

    private var nextPage = 0
    private val limit = 10

    private val _items: MutableLiveData<ArrayList<Item>> by lazy {
        MutableLiveData<ArrayList<Item>>(ArrayList()).also {
            loadMoreItems()
        }
    }

    fun items(): LiveData<ArrayList<Item>> = _items

    private val _state = MutableLiveData(State.NOT_INIT)

    fun state(): LiveData<State> = _state

    fun loadMoreItems() {
        Log.d(MainViewModel::class.java.toString(), "Loading items")
        _state.postValue(State.LOADING)

        viewModelScope.launch {
            try {
                val moreItems = repository.getItems(nextPage, limit)
                _items.value?.addAll(moreItems)
                ++nextPage
                _items.postValue(_items.value)

                Log.d(MainViewModel::class.java.toString(), "I have the items ${moreItems.size}")

                if (moreItems.size > 0)
                    _state.postValue(State.LOADED_MORE_DATA)
                else
                    _state.postValue(State.LOADED_NO_MORE_DATA)
            } catch (e: Exception) {
                _state.postValue(State.ERROR_LOADING)
            }
        }
    }

    enum class State {
        NOT_INIT,
        LOADING,
        LOADED_MORE_DATA,
        LOADED_NO_MORE_DATA,
        ERROR_LOADING
    }
}