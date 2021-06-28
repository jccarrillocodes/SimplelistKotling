package es.jccarrillo.simplelistkotlin.data.local.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import es.jccarrillo.simplelistkotlin.data.model.Owner

class RoomConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun ownerToString(owner: Owner): String {
            val gson = Gson()
            return gson.toJson(owner)
        }

        @TypeConverter
        @JvmStatic
        fun stringToOwner(value: String): Owner {
            val gson = Gson()
            return gson.fromJson(value, Owner::class.java)
        }
    }

}