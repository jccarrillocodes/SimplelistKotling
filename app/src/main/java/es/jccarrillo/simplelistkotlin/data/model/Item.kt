package es.jccarrillo.simplelistkotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.jccarrillo.simplelistkotlin.data.local.mapper.RoomConverter

@Entity(tableName = "items")
data class Item(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var full_name: String = "",
    var description: String = "",
    var fork: Boolean = false,
    @TypeConverters(RoomConverter::class)
    var owner: Owner = Owner()
)
