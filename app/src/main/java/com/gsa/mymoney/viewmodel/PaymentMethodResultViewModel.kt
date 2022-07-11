package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.database.PaymentMethodResult
import com.gsa.mymoney.database.PaymentMethodResultDBRepository
import java.util.*

class PaymentMethodResultViewModel: ViewModel() {

    private val paymentMethodResultDBRepository = PaymentMethodResultDBRepository.get()
    private val paymentMethodResultIdLiveData = MutableLiveData<UUID>()

    //добавление метода
    fun addPaymentMethodResult (paymentMethodResult: PaymentMethodResult) = paymentMethodResultDBRepository.addPaymentMethodResult(paymentMethodResult)

    //обновление метода
    fun updatePaymentMethodResult (paymentMethodResult: PaymentMethodResult) = paymentMethodResultDBRepository.updatePaymentMethodResult(paymentMethodResult)

    //удаление метода
    fun deletePaymentMethodResult (paymentMethodResult: PaymentMethodResult) = paymentMethodResultDBRepository.deletePaymentMethodResult(paymentMethodResult)

    //получение одного метода
    var paymentMethodResultLiveData: LiveData<PaymentMethodResult?> = Transformations.switchMap(paymentMethodResultIdLiveData) { paymentMethodResultID ->
        paymentMethodResultDBRepository.getPaymentMethodResult(paymentMethodResultID)
    }

    fun loadPaymentResultMethod (paymentMethodResultID: UUID) {
        paymentMethodResultIdLiveData.value = paymentMethodResultID
    }

    //получение списка методов
    val paymentMethodResultListLiveData = paymentMethodResultDBRepository.getPaymentMethodsResults()
    //получение списка методов для спинера
    val paymentMethodResultStringListLiveData = paymentMethodResultDBRepository.getStringPaymentMethodsResults()
}