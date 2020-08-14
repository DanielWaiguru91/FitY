package tech.danielwaiguru.fity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.danielwaiguru.fity.database.converter.Converters

@Database(entities = [Run::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RunDatabase: RoomDatabase() {
    abstract fun runDao(): RunDao
}