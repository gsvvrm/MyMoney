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

    //запрос методов для спинера
    @Query("select paymentName from PaymentMethod")
    fun getStringPaymentMethods(): LiveData<List<String>>

    //запрос одного метода по ID
    @Query("select * from PaymentMethod where id=(:id)")
    fun getPaymentMethodForID (id: UUID) : LiveData<PaymentMethod?>

    //запрос одного метода по имени и последнего созданного
    @Query("select * from PaymentMethod where paymentName=(:paymentName) order by dateCreate desc limit 1")
    fun getPaymentMethodForName (paymentName: String) : LiveData<PaymentMethod?>

    //запрос одного метода по имени и последнего созданного второй
    @Query("select * from PaymentMethod where paymentName=(:paymentName) order by dateCreate desc limit 1")
    fun getPaymentMethodForNameSecond (paymentName: String) : LiveData<PaymentMethod?>

    //получение баланса для одного метода оплаты
    @Query("select balance from PaymentMethod where paymentName = (:paymentName)")
    fun getBalanceForOneMethod (paymentName: String): LiveData<Float?>

    //получение общего баланса
    @Query("select sum (balance) from PaymentMethod")
    fun getSumBalance (): LiveData<Float?>


}