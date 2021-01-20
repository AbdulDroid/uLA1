package com.ulesson.androidinterview.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.navigation.NavDispatcher
import com.ulesson.androidinterview.navigation.NavDispatcherImpl
import dagger.Binds

@InstallIn(ActivityComponent::class)
@Module
interface NavModule {

    @get:Binds
    val NavDispatcherImpl.navDispatcher: NavDispatcher

    companion object{
        @Provides
        fun providesNavController(ctx: Activity): NavController =
            ctx.findNavController(R.id.nav_host_fragment)
    }
}