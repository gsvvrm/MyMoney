package com.gsa.mymoney

import android.app.Application
import com.gsa.mymoney.database.AppDBRepository

private const val TAG = "MyMoneyApplication"

class MyMoneyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //инициализация репозитория
        AppDBRepository.initialize(this)
    }
}