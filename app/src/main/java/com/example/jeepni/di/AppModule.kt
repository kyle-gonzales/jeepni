package com.example.jeepni.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import com.example.jeepni.core.data.repository.*
import com.example.jeepni.util.AlarmScheduler
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideUserDetailRepository(
        app : Application,
        auth : FirebaseAuth,
        db : FirebaseFirestore
    ) : UserDetailRepository = UserDetailRepositoryImpl(app, auth, db)

    @Provides
    @Singleton
    fun provideMapsRepository(
        auth : FirebaseAuth,
        db : FirebaseFirestore
    ) : MapsRepository = MapsRepositoryImpl(auth, db)

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

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient (
        @ApplicationContext context : Context
    ) = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun providesAlarmManager (
        @ApplicationContext context : Context
    ) = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @Provides
    @Singleton
    fun providesAlarmScheduler (
        @ApplicationContext context : Context,
        alarmManager: AlarmManager
    ) = AlarmScheduler(context, alarmManager)
}