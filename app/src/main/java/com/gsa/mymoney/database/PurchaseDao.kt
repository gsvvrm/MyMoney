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
    @Query ("select * from Purchase")
    fun getPurchases() : LiveData<List<Purchase>>

    //запрос одной покупки
    @Query ("select * from Purchase where id=(:id)")
    fun getPurchase (id: UUID) : LiveData<Purchase?>

}