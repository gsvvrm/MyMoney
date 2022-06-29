package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.MandatoryPay
import java.util.*

class MandatoryPayViewModel: ViewModel() {

    private val appDBRepository = AppDBRepository.get()
    private val mandatoryPayIdLiveData = MutableLiveData<UUID>()

    //добавление платежа
    fun addMandatoryPay (mandatoryPay: MandatoryPay) = appDBRepository.addMandatoryPay(mandatoryPay)

    //обновление платежа
    fun updateMandatoryPay (mandatoryPay: MandatoryPay) = appDBRepository.updateMandatoryPay(mandatoryPay)

    //удаление платежа
    fun deleteMandatoryPay (mandatoryPay: MandatoryPay) = appDBRepository.deleteMandatoryPay(mandatoryPay)

    //получение одного платежа
    var mandatoryPayLiveData: LiveData<MandatoryPay?> = Transformations.switchMap(mandatoryPayIdLiveData){ mandatoryPayID ->
        appDBRepository.getMandatoryPay(mandatoryPayID)
    }

    fun loadMandatoryPay (mandatoryPayID: UUID) {
        mandatoryPayIdLiveData.value=mandatoryPayID
    }

    //получение списка платежей
    val mandatoryPaisListLiveData = appDBRepository.getMandatoryPais()
}