package tech.danielwaiguru.fity.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Run::class], version = 1, exportSchema = false)
abstract class RunDatabase: RoomDatabase() {
    abstract fun runDao(): RunDao
}