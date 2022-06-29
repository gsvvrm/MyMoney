package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface PaymentMethodDao {

    @Insert
    fun addPaymentMethod (paymentMethod: PaymentMethod)

    @Update
    fun updatePaymentMethod (paymentMethod: PaymentMethod)

    @Delete
    fun deletePaymentMethod (paymentMethod: PaymentMethod)

    //запрос всех методов
    @Query("select * from PaymentMethod")
    fun getPaymentMethods() : LiveData<List<PaymentMethod>>

    //запрос одного
    @Query("select * from PaymentMethod where id=(:id)")
    fun getPaymentMethod (id: UUID) : LiveData<PaymentMethod?>

}