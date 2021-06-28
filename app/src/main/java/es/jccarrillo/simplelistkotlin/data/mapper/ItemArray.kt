package es.jccarrillo.simplelistkotlin.data.mapper

import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse

sealed class ItemArray {

    companion object {

        fun toItemsResponse(list: Array<Item>): ItemsResponse {
            val res = ItemsResponse()
            list.forEach {
                res.add(toItem(it))
            }
            return res
        }

        private fun toItem(item: Item): Item {
            val res = Item()
            res.description = item.description
            res.fork = item.fork
            res.full_name = item.full_name
            res.id = item.id
            res.name = item.name
            res.owner = item.owner
            return res
        }

    }

}