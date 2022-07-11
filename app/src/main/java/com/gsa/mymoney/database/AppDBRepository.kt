package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "AppDBRepository"
private const val DATABASE_NAME = "money-database"

class AppDBRepository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()
























    companion object{
        private var INSTANCE: AppDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AppDBRepository(context)
            Log.d(TAG,"AppDBRepository is initialized")

            }

        }

        fun get(): AppDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("AppDBRepository must be initialized")
        }
    }
}