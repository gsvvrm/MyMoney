package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface PurchaseDao {

    @Insert
    fun addPurchase (purchase: Purchase)

    @Update
    fun updatePurchase (purchase: Purchase)

    @Delete
    fun deletePurchase (purchase: Purchase)

    //запрос всех покупок
    @Query ("select * from Purchase order by date DESC")
    fun getPurchases() : LiveData<List<Purchase>>

    //запрос одной покупки
    @Query ("select * from Purchase where id=(:id)")
    fun getPurchase (id: UUID) : LiveData<Purchase?>

    //запрос баланса в текущем месяце

    //запрос затрат для категории
    @Query ("select sum(price) from Purchase where category = (:category) and date between :date1 and :date2 order by date desc")
    fun getPriceForCategory (category: String, date1: Date, date2: Date) : LiveData<Float?>

    //запрос дат действий
    @Query("select distinct date from Purchase order by date desc")
    fun getDatePurchaseList () : LiveData<List<Date>>

    //запрос действий за дату
    @Query ("select * from Purchase where date=(:date)")
    fun getPurchasesForDate(date: Date) : LiveData<List<Purchase>>



}