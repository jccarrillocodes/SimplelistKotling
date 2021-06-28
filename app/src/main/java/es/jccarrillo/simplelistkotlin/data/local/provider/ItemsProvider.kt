package es.jccarrillo.simplelistkotlin.data.local.provider

import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse

interface ItemsProvider {

    fun getItems(page: Int, limit: Int): ItemsResponse

    fun storeItems(items: ItemsResponse)

    fun clearStorage()
}