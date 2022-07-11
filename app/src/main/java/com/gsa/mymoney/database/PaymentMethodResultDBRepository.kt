package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "PaymentMethodResultDBRepository"
private const val DATABASE_NAME = "money-database"

class PaymentMethodResultDBRepository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()



    //Методы оплаты итоговые
    private val paymentMethodResultDao = database.paymentMethodResultDao()
    //добавление метода
    fun addPaymentMethodResult (paymentMethodResult: PaymentMethodResult) {
        executor.execute { paymentMethodResultDao.addPaymentMethodResult(paymentMethodResult) }
    }
    //изменение метода
    fun updatePaymentMethodResult (paymentMethodResult: PaymentMethodResult) {
        executor.execute { paymentMethodResultDao.updatePaymentMethodResult(paymentMethodResult) }
    }
    //удаление метода
    fun deletePaymentMethodResult (paymentMethodResult: PaymentMethodResult) {
        executor.execute { paymentMethodResultDao.deletePaymentMethodResult(paymentMethodResult) }
    }
    //запрос списка всех методов
    fun getPaymentMethodsResults(): LiveData<List<PaymentMethodResult>> = paymentMethodResultDao.getPaymentMethodsResults()
    //запрос списка всех методов для спинера
    fun getStringPaymentMethodsResults(): LiveData<List<String>> = paymentMethodResultDao.getStringPaymentMethodsResults()
    //запрос одного метода
    fun getPaymentMethodResult(id: UUID): LiveData<PaymentMethodResult?> = paymentMethodResultDao.getPaymentMethodResult(id)



    companion object{
        private var INSTANCE: PaymentMethodResultDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PaymentMethodResultDBRepository(context)
                Log.d(TAG,"PaymentMethodResultDBRepository is initialized")

            }

        }

        fun get(): PaymentMethodResultDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("PaymentMethodResultDBRepository must be initialized")
        }
    }
}