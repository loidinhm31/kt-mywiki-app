package com.july.wikipedia.database.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.july.wikipedia.database.dao.ItemDao
import com.july.wikipedia.database.entities.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase: RoomDatabase() {
    abstract val itemDao: ItemDao
}

private lateinit var INSTANCE: ItemDatabase

fun getItemDatabase(context: Context): ItemDatabase {
    synchronized(ItemDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ItemDatabase::class.java,
                "items").build()
        }
    }
    return INSTANCE
}
