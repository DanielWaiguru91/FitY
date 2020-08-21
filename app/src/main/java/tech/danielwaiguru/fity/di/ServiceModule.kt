package tech.danielwaiguru.fity.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import tech.danielwaiguru.fity.R
import tech.danielwaiguru.fity.common.Constants
import tech.danielwaiguru.fity.ui.views.MainActivity

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
    @ServiceScoped
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context) =
        FusedLocationProviderClient(context)
    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder =  NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_baseline_run)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(context.getString(R.string.timer))
        .setAutoCancel(false)
        .setOngoing(true)
        .setContentIntent(pendingIntent)
    @ServiceScoped
    @Provides
    fun providePendingIntent(
        @ApplicationContext context: Context
    ): PendingIntent = PendingIntent.getActivity(
        context,
        0,
        Intent(context, MainActivity::class.java).also {
            it.action = Constants.ACTION_RESUME_RUNNING_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}