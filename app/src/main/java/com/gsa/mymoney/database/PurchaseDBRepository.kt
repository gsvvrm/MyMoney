package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "PurchaseDBRepository"
private const val DATABASE_NAME = "money-database"

class PurchaseDBRepository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()

    //Покупки
    private val purchaseDao = database.purchaseDao()
    // добавление покупки
    fun addPurchase (purchase: Purchase) {
        executor.execute { purchaseDao.addPurchase(purchase) }
    }
    //изменеие покупки
    fun updatePurchase (purchase: Purchase){
        executor.execute { purchaseDao.updatePurchase(purchase) }
    }
    //удаление покупки
    fun deletePurchase (purchase: Purchase) {
        executor.execute { purchaseDao.deletePurchase(purchase) }
    }
    //запрос списка всех покупок
    fun getPurchases(): LiveData<List<Purchase>> = purchaseDao.getPurchases()
    //запрос одной покупки
    fun getPurchase(id: UUID): LiveData<Purchase?> = purchaseDao.getPurchase(id)
    //запрос баланса в текущем месяце

    //запрос затрат для категории
    fun getPriceForCategory (category: String, date1: Date, date2: Date) : LiveData<Float?> =
        purchaseDao.getPriceForCategory(category,date1,date2)


    companion object{
        private var INSTANCE: PurchaseDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PurchaseDBRepository(context)
                Log.d(TAG,"PurchaseDBRepository is initialized")

            }

        }

        fun get(): PurchaseDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("PurchaseDBRepository must be initialized")
        }
    }

    //запрос дат действий
    fun getDatePurchaseList () : LiveData<List<String>> = purchaseDao.getDatePurchaseList()

    //запрос действий за дату
    fun getPurchasesForDate(dateTypeS:String) : LiveData<List<Purchase>> = purchaseDao.getPurchasesForDate(dateTypeS)
}