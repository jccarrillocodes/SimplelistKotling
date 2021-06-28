package es.jccarrillo.simplelistkotlin.data.local.provider

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.jccarrillo.simplelistkotlin.data.model.Item

@Dao
interface ItemsDAO {

    @Query("SELECT * FROM items ORDER BY id ASC")
    fun getAllItems(): Array<Item>

    @Query("SELECT * FROM items ORDER BY id ASC LIMIT :limit OFFSET (:page*:limit)")
    fun getAllItems(page: Int, limit: Int): Array<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Query("DELETE FROM items")
    fun deleteAll()


}