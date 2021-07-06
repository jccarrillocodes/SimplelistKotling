package es.jccarrillo.simplelistkotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.data.repository.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun reload() {
        _state.postValue(State.LOADING)

        viewModelScope.launch {
            nextPage = 0
            try {
                val remoteItems = withContext(Dispatchers.IO) {
                    repository.getRemoteItems(nextPage, limit)
                }

                _items.value?.clear()
                _items.value?.addAll(remoteItems)
                ++nextPage

                withContext(Dispatchers.IO) {
                    repository.clearLocalItems()
                }

                _items.postValue(_items.value)

                if (remoteItems.size > 0)
                    _state.postValue(State.LOADED_MORE_DATA)
                else
                    _state.postValue(State.LOADED_NO_MORE_DATA)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.postValue(State.ERROR_LOADING)
            }
        }
    }

    fun loadMoreItems() {
        _state.postValue(State.LOADING)

        viewModelScope.launch {
            try {
                val moreItems = withContext(Dispatchers.IO) {
                    repository.getItems(nextPage, limit)
                }
                _items.value?.addAll(moreItems)
                ++nextPage
                _items.postValue(_items.value)

                if (moreItems.size > 0)
                    _state.postValue(State.LOADED_MORE_DATA)
                else
                    _state.postValue(State.LOADED_NO_MORE_DATA)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.postValue(State.ERROR_LOADING)
            }
        }
    }

    fun showedLastItem() {
        if (_state.value == State.LOADED_MORE_DATA) {
            loadMoreItems()
        }
    }

    fun onItemClicked(item: Item) {
        // TODO("Not yet implemented")
    }

    enum class State {
        NOT_INIT,
        LOADING,
        LOADED_MORE_DATA,
        LOADED_NO_MORE_DATA,
        ERROR_LOADING
    }
}