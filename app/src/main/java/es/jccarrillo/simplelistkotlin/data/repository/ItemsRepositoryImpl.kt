package es.jccarrillo.simplelistkotlin.data.repository

import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse


class ItemsRepositoryImpl(
    private val local: es.jccarrillo.simplelistkotlin.data.local.provider.ItemsProvider,
    private val remote: es.jccarrillo.simplelistkotlin.data.remote.provider.ItemsProvider
) : ItemsRepository {

    override fun getItems(page: Int, limit: Int): ItemsResponse {
        var response = local.getItems(page, limit)
        if (response.size == 0) {
            response = remote.getItems(page, limit)
            local.storeItems(response)
        }
        return response
    }

    override fun getRemoteItems(page: Int, limit: Int): ItemsResponse = remote.getItems(page, limit)

    override fun clearLocalItems() {
        local.clearStorage()
    }
}