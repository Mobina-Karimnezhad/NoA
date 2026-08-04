package com.noa.app.di

import com.noa.app.core.ai.AIProvider
import com.noa.app.core.ai.OpenRouterProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AIModule {

    @Binds
    abstract fun bindAIProvider(
        impl: OpenRouterProvider
    ): AIProvider

}