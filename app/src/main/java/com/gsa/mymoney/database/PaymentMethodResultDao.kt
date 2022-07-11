package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface PaymentMethodResultDao {

    @Insert
    fun addPaymentMethodResult (paymentMethodResult: PaymentMethodResult)

    @Update
    fun updatePaymentMethodResult (paymentMethodResult: PaymentMethodResult)

    @Delete
    fun deletePaymentMethodResult (paymentMethodResult: PaymentMethodResult)

    //запрос всех методов
    @Query("select * from PaymentMethodResult")
    fun getPaymentMethodsResults() : LiveData<List<PaymentMethodResult>>

    //запрос методов для спинера
    @Query("select paymentName from PaymentMethodResult")
    fun getStringPaymentMethodsResults(): LiveData<List<String>>

    //запрос одного
    @Query("select * from PaymentMethodResult where id=(:id)")
    fun getPaymentMethodResult (id: UUID) : LiveData<PaymentMethodResult?>

}