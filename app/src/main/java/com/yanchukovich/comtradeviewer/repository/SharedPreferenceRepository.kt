package com.yanchukovich.comtradeviewer.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val SHARED_PREF_VAL = "sharedPrefFile"
private const val IS_FIRST_OPEN = "isFirstOpen"

@Singleton
class SharedPreferenceRepository @Inject constructor(
    @ApplicationContext context: Context
){

    private var sharedPreference : SharedPreferences

    init{
        sharedPreference = context.getSharedPreferences(SHARED_PREF_VAL, Context.MODE_PRIVATE)
    }

    fun setIsFirstOpen(isFirstOpen : Boolean) {
        sharedPreference.edit {
            putBoolean(IS_FIRST_OPEN, isFirstOpen)
        }
    }

    fun getIsFirstOpen() : Boolean {
        return sharedPreference.getBoolean(IS_FIRST_OPEN, true)
    }
}