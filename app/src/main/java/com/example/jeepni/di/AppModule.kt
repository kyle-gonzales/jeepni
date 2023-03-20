package com.example.jeepni.di

import android.app.Application
import com.example.jeepni.core.data.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideJeepsRepository(
        app: Application,
        auth : FirebaseAuth,
        db : FirebaseFirestore
    ) : JeepsRepository = JeepsRepositoryImpl(app, auth, db)

    @Provides
    @Singleton
    fun provideDailyAnalyticsRepository(
        auth : FirebaseAuth,
        usersRef : CollectionReference,
        app: Application
    ) : DailyAnalyticsRepository = DailyAnalyticsRepositoryImpl(auth, usersRef, app)

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