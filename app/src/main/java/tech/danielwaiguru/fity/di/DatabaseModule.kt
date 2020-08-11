package tech.danielwaiguru.fity.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.danielwaiguru.fity.database.RunDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            RunDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    @Singleton
    @Provides
    fun provideDao(runDatabase: RunDatabase) = runDatabase.runDao()
}
const val DB_NAME = "runs"
