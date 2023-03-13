package com.example.jeepni.di

import android.app.Application
import com.example.jeepni.core.data.repository.DailyAnalyticsRepository
import com.example.jeepni.core.data.repository.DailyAnalyticsRepositoryImpl
import com.example.jeepni.core.data.repository.AuthRepository
import com.example.jeepni.core.data.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUsersCollectionReference() = Firebase.firestore.collection("users")

    @Provides
    @Singleton
    fun provideDailyAnalyticsRepository(
        usersRef : CollectionReference,
        app: Application
    ) : DailyAnalyticsRepository = DailyAnalyticsRepositoryImpl(usersRef, app)

    @Provides
    @Singleton
    fun provideAuthRepository(
        app : Application,
        auth : FirebaseAuth
    ) : AuthRepository = AuthRepositoryImpl(app, auth)


    @Provides
    @Singleton

    fun provideFirebaseAuth () = Firebase.auth

}