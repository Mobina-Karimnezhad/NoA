package com.noa.app.di

import com.noa.app.data.notification.StaticNotificationMessageProvider
import com.noa.app.domain.notification.NotificationMessageProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindNotificationMessageProvider(
        implementation: StaticNotificationMessageProvider
    ): NotificationMessageProvider

}