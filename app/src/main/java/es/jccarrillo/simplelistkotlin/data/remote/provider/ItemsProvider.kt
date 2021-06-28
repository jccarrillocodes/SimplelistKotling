package es.jccarrillo.simplelistkotlin.data.remote.provider

import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse

interface ItemsProvider {

    fun getItems(page: Int, limit: Int): ItemsResponse
}