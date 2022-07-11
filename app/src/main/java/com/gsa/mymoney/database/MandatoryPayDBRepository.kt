package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "MandatoryPayDBRepository"
private const val DATABASE_NAME = "money-database"

class MandatoryPayDBRepository private constructor(context: Context)  {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()


    //обязательные платежи
    private val mandatoryPayDao = database.mandatoryPayDao()
    // добавление покупки
    fun addMandatoryPay (mandatoryPay: MandatoryPay) {
        executor.execute { mandatoryPayDao.addMandatoryPay(mandatoryPay) }
    }
    //изменеие покупки
    fun updateMandatoryPay (mandatoryPay: MandatoryPay){
        executor.execute { mandatoryPayDao.updateMandatoryPay(mandatoryPay) }
    }
    //удаление покупки
    fun deleteMandatoryPay (mandatoryPay: MandatoryPay) {
        executor.execute { mandatoryPayDao.deleteMandatoryPay(mandatoryPay) }
    }
    //запрос списка всех покупок
    fun getMandatoryPais(): LiveData<List<MandatoryPay>> = mandatoryPayDao.getMandatoryPais()
    //запрос одной покупки
    fun getMandatoryPay(id: UUID): LiveData<MandatoryPay?> = mandatoryPayDao.getMandatoryPay(id)



    companion object{
        private var INSTANCE: MandatoryPayDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MandatoryPayDBRepository(context)
                Log.d(TAG,"MandatoryPayDBRepository is initialized")

            }

        }

        fun get(): MandatoryPayDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("MandatoryPayDBRepository must be initialized")
        }
    }
}