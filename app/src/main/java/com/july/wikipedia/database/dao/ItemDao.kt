package com.july.wikipedia.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.july.wikipedia.database.entities.Item

@Dao
interface ItemDao {
    @Query("SELECT i.* " +
            "FROM item i " +
            "ORDER BY i.updatedDate " +
            "LIMIT :limit")
    fun findItems(limit: Int): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pages: List<Item>)

    @Query("UPDATE item SET isFav = :isFav WHERE id = :itemId")
    fun updateFavorite(itemId: Long, isFav: Boolean)

    @Query("SELECT i.isFav " +
            "FROM item i " +
            "WHERE i.id = :itemId")
    fun findFavoriteItem(itemId: Long): Boolean

    @Query("SELECT i.* " +
            "FROM item i " +
            "WHERE i.isFav = :isFav")
    fun findItemByFavorite(isFav: Boolean): List<Item>

    @Query("UPDATE item SET isSeen = :isSeen WHERE id = :itemId")
    fun updateSeen(itemId: Long, isSeen: Boolean)

    @Query("SELECT i.* " +
            "FROM item i " +
            "WHERE i.isSeen = :isSeen")
    fun findItemBySeen(isSeen: Boolean): List<Item>

    @Query("DELETE FROM item " +
            "WHERE isSeen = :isSeen")
    fun deleteItemBySeen(isSeen: Boolean)
}