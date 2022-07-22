package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.database.PurchaseDBRepository
import java.util.*

class PurchaseViewModel: ViewModel() {

    private val purchaseDBRepository = PurchaseDBRepository.get()
    private val purchaseIdLiveData = MutableLiveData<UUID>()

    //добавление покупки
    fun addPurchase (purchase: Purchase) = purchaseDBRepository.addPurchase(purchase)

    //обновление покупки
    fun updatePurchase (purchase: Purchase) = purchaseDBRepository.updatePurchase(purchase)

    //удаление покупки
    fun deletePurchase (purchase: Purchase) = purchaseDBRepository.deletePurchase(purchase)

    //получение одной покупки
    var purchaseLiveData: LiveData<Purchase?> = Transformations.switchMap(purchaseIdLiveData){ purchaseID ->
        purchaseDBRepository.getPurchase(purchaseID)
    }

    fun loadPurchase (purchaseID: UUID) {
        purchaseIdLiveData.value=purchaseID
    }

    //получение списка покупок
    val purchasesListLiveData = purchaseDBRepository.getPurchases()

    //запрос баланса в текущем месяце

    //запрос затрат для категории
    fun getPriceForCategory (category: String, date1: Date, date2: Date) : LiveData<Float?> =
        purchaseDBRepository.getPriceForCategory(category,date1,date2)

    //запрос дат действий
    fun getDatePurchaseList () : LiveData<List<String>> = purchaseDBRepository.getDatePurchaseList()

    //запрос действий за дату
    fun getPurchasesForDate(dateTypeS:String) : LiveData<List<Purchase>> = purchaseDBRepository.getPurchasesForDate(dateTypeS)

}