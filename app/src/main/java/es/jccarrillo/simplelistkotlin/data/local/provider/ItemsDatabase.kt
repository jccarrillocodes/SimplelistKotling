package es.jccarrillo.simplelistkotlin.data.local.provider

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.jccarrillo.simplelistkotlin.data.local.mapper.RoomConverter
import es.jccarrillo.simplelistkotlin.data.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDAO

    companion object {

        @Volatile
        private var INSTANCE: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemsDatabase::class.java,
                    "items_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}