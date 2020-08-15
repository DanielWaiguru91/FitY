package tech.danielwaiguru.fity.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import tech.danielwaiguru.fity.common.Constants.IS_USER_LOGGED_IN
import tech.danielwaiguru.fity.common.Constants.NAME_KEY
import tech.danielwaiguru.fity.common.Constants.PREFS_NAME
import tech.danielwaiguru.fity.common.Constants.WEIGHT_KEY
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(NAME_KEY, "") ?: ""
    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(WEIGHT_KEY, 60f)
    @Singleton
    @Provides
    fun provideUserLoggedInState(sharedPreferences: SharedPreferences) =
        sharedPreferences.getBoolean(IS_USER_LOGGED_IN, false)
}