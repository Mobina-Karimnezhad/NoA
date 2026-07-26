package com.noa.app.di

import com.noa.app.data.reminder.AlarmManagerReminderScheduler
import com.noa.app.domain.reminder.ReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderModule {

    @Binds
    @Singleton
    abstract fun bindReminderScheduler(
        implementation:
        AlarmManagerReminderScheduler
    ): ReminderScheduler

}