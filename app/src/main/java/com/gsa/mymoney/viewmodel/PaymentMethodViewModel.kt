package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.database.PaymentMethodDBRepository
import java.util.*

class PaymentMethodViewModel: ViewModel() {

    private val paymentMethodDBRepository = PaymentMethodDBRepository.get()
    private val paymentMethodIdLiveData = MutableLiveData<UUID>()
    private val paymentMethodNameLiveData = MutableLiveData<String>()

    //добавление метода
    fun addPaymentMethod (paymentMethod: PaymentMethod) = paymentMethodDBRepository.addPaymentMethod(paymentMethod)

    //обновление метода
    fun updatePaymentMethod (paymentMethod: PaymentMethod) = paymentMethodDBRepository.updatePaymentMethod(paymentMethod)

    //удаление метода
    fun deletePaymentMethod (paymentMethod: PaymentMethod) = paymentMethodDBRepository.deletePaymentMethod(paymentMethod)

    //получение одного метода по ID
    var paymentMethodLiveDataForID: LiveData<PaymentMethod?> = Transformations.switchMap(paymentMethodIdLiveData) { paymentMethodID ->
        paymentMethodDBRepository.getPaymentMethodForID(paymentMethodID)
    }

    fun loadPaymentMethodForID (paymentMethodID: UUID) {
        paymentMethodIdLiveData.value = paymentMethodID
    }

    //получение одного метода по имени
    var paymentMethodLiveDataForName: LiveData<PaymentMethod?> = Transformations.switchMap(paymentMethodNameLiveData) { paymentMethodName ->
        paymentMethodDBRepository.getPaymentMethodForName(paymentMethodName)
    }

    fun loadPaymentMethodForName (paymentMethodName: String) {
        paymentMethodNameLiveData.value = paymentMethodName
    }

    //получение одного метода по имени второй
    var paymentMethodLiveDataForNameSecond: LiveData<PaymentMethod?> = Transformations.switchMap(paymentMethodNameLiveData) { paymentMethodName ->
        paymentMethodDBRepository.getPaymentMethodForNameSecond(paymentMethodName)
    }

    fun loadPaymentMethodForNameSecond (paymentMethodName: String) {
        paymentMethodNameLiveData.value = paymentMethodName
    }

    //получение списка методов
    val paymentMethodListLiveData = paymentMethodDBRepository.getPaymentMethods()
    //получение списка методов для спинера
    val paymentMethodStringListLiveData = paymentMethodDBRepository.getStringPaymentMethods()

    //получение баланса для одного метода оплаты
    fun getBalanceForOneMethod (paymentName: String): LiveData<Float?> = paymentMethodDBRepository.getBalanceForOneMethod(paymentName)

    //получение сумарного баланса
    var sumBalance  = paymentMethodDBRepository.getSumBalance()

}