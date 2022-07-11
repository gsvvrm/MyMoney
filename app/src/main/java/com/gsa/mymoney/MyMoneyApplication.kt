package com.gsa.mymoney

import android.app.Application
import com.gsa.mymoney.database.*

private const val TAG = "MyMoneyApplication"

class MyMoneyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //инициализация репозиториев
        CategoryDBRepository.initialize(this)
        CategoryResultDBRepository.initialize(this)
        MandatoryPayDBRepository.initialize(this)
        PaymentMethodDBRepository.initialize(this)
        PaymentMethodResultDBRepository.initialize(this)
        PurchaseDBRepository.initialize(this)

    }
}