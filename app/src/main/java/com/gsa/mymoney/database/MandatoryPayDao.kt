package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface MandatoryPayDao {

    @Insert
    fun addMandatoryPay (mandatoryPay: MandatoryPay)

    @Update
    fun updateMandatoryPay (mandatoryPay: MandatoryPay)

    @Delete
    fun deleteMandatoryPay (mandatoryPay: MandatoryPay)

    //запрос всех покупок
    @Query("select * from MandatoryPay")
    fun getMandatoryPais() : LiveData<List<MandatoryPay>>

    //запрос одной покупки
    @Query("select * from MandatoryPay where id=(:id)")
    fun getMandatoryPay (id: UUID) : LiveData<MandatoryPay?>

}