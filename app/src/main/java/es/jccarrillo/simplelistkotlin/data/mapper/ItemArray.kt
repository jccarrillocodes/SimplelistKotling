package es.jccarrillo.simplelistkotlin.data.mapper

import es.jccarrillo.simplelistkotlin.data.model.Item
import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse

sealed class ItemArray {

    companion object {

        fun toItemsResponse(list: Array<Item>): ItemsResponse {
            val res = ItemsResponse()
            list.forEach {
                res.add(it)
            }
            return res
        }

    }

}