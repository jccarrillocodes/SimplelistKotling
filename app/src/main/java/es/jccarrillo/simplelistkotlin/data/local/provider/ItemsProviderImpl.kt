package es.jccarrillo.simplelistkotlin.data.local.provider

import es.jccarrillo.simplelistkotlin.data.mapper.ItemArray
import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse

class ItemsProviderImpl(val dao: ItemsDAO) : ItemsProvider {

    override fun getItems(page: Int, limit: Int): ItemsResponse {
        return ItemArray.toItemsResponse(dao.getAllItems(page, limit))
    }

    override fun storeItems(items: ItemsResponse) {
        items.forEach {
            dao.insertItem(it)
        }
    }

    override fun clearStorage() {
        dao.deleteAll()
    }
}