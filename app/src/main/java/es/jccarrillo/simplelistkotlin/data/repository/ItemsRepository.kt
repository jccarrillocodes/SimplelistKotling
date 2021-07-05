package es.jccarrillo.simplelistkotlin.data.repository

import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse

interface ItemsRepository {

    fun getItems(page: Int, limit: Int): ItemsResponse

    fun getRemoteItems(page: Int, limit: Int): ItemsResponse

    fun clearLocalItems()

}