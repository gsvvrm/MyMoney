package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "PaymentMethodDBRepository"
private const val DATABASE_NAME = "money-database"

class PaymentMethodDBRepository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()

    //Методы оплаты
    private val paymentMethodDao = database.paymentMethodDao()
    //добавление метода
    fun addPaymentMethod (paymentMethod: PaymentMethod) {
        executor.execute { paymentMethodDao.addPaymentMethod(paymentMethod) }
    }
    //изменение метода
    fun updatePaymentMethod (paymentMethod: PaymentMethod) {
        executor.execute { paymentMethodDao.updatePaymentMethod(paymentMethod) }
    }
    //удаление метода
    fun deletePaymentMethod (paymentMethod: PaymentMethod) {
        executor.execute { paymentMethodDao.deletePaymentMethod(paymentMethod) }
    }
    //запрос списка всех методов
    fun getPaymentMethods(): LiveData<List<PaymentMethod>> = paymentMethodDao.getPaymentMethods()
    //запрос списка всех методов для спинера
    fun getStringPaymentMethods(): LiveData<List<String>> = paymentMethodDao.getStringPaymentMethods()
    //запрос одного метода по ID
    fun getPaymentMethodForID(id: UUID): LiveData<PaymentMethod?> = paymentMethodDao.getPaymentMethodForID(id)
    //запрос одного метода по имени
    fun getPaymentMethodForName(paymentName: String): LiveData<PaymentMethod?> = paymentMethodDao.getPaymentMethodForName(paymentName)
    //запрос одного метода по имени второй
    fun getPaymentMethodForNameSecond(paymentName: String): LiveData<PaymentMethod?> = paymentMethodDao.getPaymentMethodForNameSecond(paymentName)
    //получение баланса для одного метода оплаты
    fun getBalanceForOneMethod (paymentName: String): LiveData<Float?> = paymentMethodDao.getBalanceForOneMethod(paymentName)
    //запрос общего баланса
    fun getSumBalance (): LiveData<Float?> = paymentMethodDao.getSumBalance()


    companion object{
        private var INSTANCE: PaymentMethodDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PaymentMethodDBRepository(context)
                Log.d(TAG,"PaymentMethodDBRepository is initialized")

            }

        }

        fun get(): PaymentMethodDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("PaymentMethodDBRepository must be initialized")
        }
    }


}