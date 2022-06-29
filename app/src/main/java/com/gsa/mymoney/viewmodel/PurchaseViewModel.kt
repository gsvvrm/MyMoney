package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.Purchase
import java.util.*

class PurchaseViewModel: ViewModel() {

    private val appDBRepository = AppDBRepository.get()
    private val purchaseIdLiveData = MutableLiveData<UUID>()

    //добавление покупки
    fun addPurchase (purchase: Purchase) = appDBRepository.addPurchase(purchase)

    //обновление покупки
    fun updatePurchase (purchase: Purchase) = appDBRepository.updatePurchase(purchase)

    //удаление покупки
    fun deletePurchase (purchase: Purchase) = appDBRepository.deletePurchase(purchase)

    //получение одной покупки
    var purchaseLiveData: LiveData<Purchase?> = Transformations.switchMap(purchaseIdLiveData){ purchaseID ->
        appDBRepository.getPurchase(purchaseID)
    }

    fun loadPurchase (purchaseID: UUID) {
        purchaseIdLiveData.value=purchaseID
    }

    //получение списка покупок
    val purchasesListLiveData = appDBRepository.getPurchases()

}