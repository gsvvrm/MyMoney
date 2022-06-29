package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.PaymentMethod
import java.util.*

class PaymentMethodViewModel: ViewModel() {

    private val appDBRepository = AppDBRepository.get()
    private val paymentMethodIdLiveData = MutableLiveData<UUID>()

    //добавление метода
    fun addPaymentMethod (paymentMethod: PaymentMethod) = appDBRepository.addPaymentMethod(paymentMethod)

    //обновление метода
    fun updatePaymentMethod (paymentMethod: PaymentMethod) = appDBRepository.updatePaymentMethod(paymentMethod)

    //удаление метода
    fun deletePaymentMethod (paymentMethod: PaymentMethod) = appDBRepository.deletePaymentMethod(paymentMethod)

    //получение одного метода
    var paymentMethodLiveData: LiveData<PaymentMethod?> = Transformations.switchMap(paymentMethodIdLiveData) { paymentMethodID ->
        appDBRepository.getPaymentMethod(paymentMethodID)
    }

    fun loadPaymentMethod (paymentMethodID: UUID) {
        paymentMethodIdLiveData.value = paymentMethodID
    }

    //получение списка методов
    val paymentMethodListLiveData = appDBRepository.getPaymentMethods()

}