package com.gsa.mymoney.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.database.AppDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "AppDBRepository"
private const val DATABASE_NAME = "app-database"

class AppDBRepository private constructor(context: Context) {

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

    //Категории
    private val categoryDao = database.categoryDao()
    //добавление категории
    fun addCategory (category: Category){
        executor.execute { categoryDao.addCategory(category) }
    }
    //изменение категории
    fun updateCategory (category: Category) {
        executor.execute { categoryDao.updateCategory(category) }
    }
    //удаление категории
    fun deleteCategory (category: Category) {
        executor.execute { categoryDao.deleteCategory(category) }
    }
    //запрос списка всех категорий
    fun getCategories(): LiveData<List<Category>> = categoryDao.getCategories()
    //запрос одной категории
    fun getCategory(id: UUID): LiveData<Category?> = categoryDao.getCategory(id)

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
    //запрос одного метода
    fun getPaymentMethod(id: UUID): LiveData<PaymentMethod?> = paymentMethodDao.getPaymentMethod(id)

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
        private var INSTANCE: AppDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AppDBRepository(context)
            }
        }

        fun get(): AppDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("AppDBRepository must be initialized")
        }
    }
}