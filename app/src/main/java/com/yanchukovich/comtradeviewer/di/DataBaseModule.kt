package com.yanchukovich.comtradeviewer.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yanchukovich.comtradeviewer.db.AppDataBase
import com.yanchukovich.comtradeviewer.db.RecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database")
            .build()
    }

    @Provides
    @Singleton
    fun provideCarDao(appDB: AppDataBase): RecordDao {
        return appDB.getRecordDao()
    }

    @Provides
    @Singleton
    fun provideFireBaseDataBase(): FirebaseDatabase {
        return Firebase.database
    }
}