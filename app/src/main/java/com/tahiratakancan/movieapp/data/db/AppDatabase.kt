package com.tahiratakancan.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tahiratakancan.movieapp.data.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}