package es.jccarrillo.simplelistkotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import es.jccarrillo.simplelistkotlin.App
import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.databinding.ActivityMainBinding
import es.jccarrillo.simplelistkotlin.ui.adapter.ItemsAdapter
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ItemsAdapter

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        adapter = ItemsAdapter()

        adapter.events = object : ItemsAdapter.AdapterEvents {

            override fun onLastItemShowed() {
                viewModel.showedLastItem()
            }

            override fun onItemClicked(item: Item) {
                viewModel.onItemClicked(item)
            }

        }

        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.reload()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)

        viewModel.items().observe(this, adapter::setItems)
        viewModel.state().observe(this, this::setState)
    }

    private fun setState(state: MainViewModel.State) {
        when (state) {
            MainViewModel.State.NOT_INIT -> binding.swipeRefresh.isRefreshing = true
            MainViewModel.State.ERROR_LOADING -> binding.swipeRefresh.isRefreshing = false
            MainViewModel.State.LOADING -> binding.swipeRefresh.isRefreshing = true
            MainViewModel.State.LOADED_MORE_DATA -> binding.swipeRefresh.isRefreshing = false
            MainViewModel.State.LOADED_NO_MORE_DATA -> binding.swipeRefresh.isRefreshing = false
        }
    }
}