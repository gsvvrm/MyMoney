package com.gsa.mymoney.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gsa.mymoney.R
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.viewmodel.PaymentMethodViewModel
import com.gsa.mymoney.viewmodel.PurchaseViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TransferEditor"

class TransferEditor : AppCompatActivity() {

    private lateinit var purchase: Purchase
    private var paymentMethodFrom = PaymentMethod()
    private var paymentMethodTo = PaymentMethod()

    private val calendar: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    private lateinit var spinnerOptionPayFrom: Spinner
    private lateinit var spinnerOptionPayTo: Spinner
    private lateinit var editTextPriceTransfer: EditText
    private lateinit var btnAddTransfer: Button

    private val paymentMethodViewModel: PaymentMethodViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PaymentMethodViewModel::class.java)
    }

    private val purchaseViewModel: PurchaseViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PurchaseViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_editor)

        spinnerOptionPayFrom = findViewById(R.id.spinnerOptionPayFrom)

        spinnerOptionPayTo = findViewById(R.id.spinnerOptionPayTo)

        editTextPriceTransfer = findViewById(R.id.editTextPriceTransfer)

        btnAddTransfer = findViewById(R.id.btnAddTransfer)

        btnAddTransfer.isEnabled=false



        paymentMethodViewModel.paymentMethodStringListLiveData.observe(this, Observer {
                paymentMethodList -> paymentMethodList

            val adapterSpinnerPayment: ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.spinner_item,paymentMethodList)

            spinnerOptionPayFrom.adapter = adapterSpinnerPayment
            spinnerOptionPayTo.adapter = adapterSpinnerPayment

        })

        buttonBlock()

        btnAddTransfer.setOnClickListener { view ->
            setTransfer()
            closeActivity()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        paymentMethodViewModel.updatePaymentMethod(paymentMethodFrom)
        Log.d(TAG, "Обновленный  $paymentMethodFrom")
        paymentMethodViewModel.updatePaymentMethod(paymentMethodTo)
        Log.d(TAG, "Обновленный  $paymentMethodTo")

    }

    private fun buttonBlock() {

        editTextPriceTransfer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    btnAddTransfer.isEnabled = false
                } else if (length > 0) {
                    btnAddTransfer.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })
    }



    private fun setTransfer() {

        Log.d(TAG, "Начало записи В")

        paymentMethodViewModel.paymentMethodListLiveData.observe(this, Observer {
                paymentMethods -> paymentMethods?.let {
            Log.d(TAG, "Загрузка списка $paymentMethods")
            paymentMethodFrom = paymentMethods.last { it.paymentName == spinnerOptionPayFrom.selectedItem.toString() }
            Log.d(TAG, "Выбор из  $paymentMethodFrom")
            paymentMethodTo = paymentMethods.last { it.paymentName == spinnerOptionPayTo.selectedItem.toString() }
            Log.d(TAG, "Выбор в $paymentMethodTo")
            addTransfer()
            newBalanceSet()

        }
        })

    }

    //функция добавления перевода в базу
    private fun addTransfer(){

        purchase = Purchase(
            UUID.randomUUID(),
            calendar.time,
            "Перевод ${editTextPriceTransfer.text.toString()} из ${spinnerOptionPayFrom.selectedItem.toString()} в ${spinnerOptionPayTo.selectedItem.toString()}",
            "",
             "",
            0f,
            "",
            sdf.format(calendar.time).toString())



        purchaseViewModel.addPurchase(purchase)

    }

    private fun newBalanceSet(){
        paymentMethodFrom.balance = paymentMethodFrom.balance - editTextPriceTransfer.text.toString().replace(",",".").toFloat()
        paymentMethodTo.balance = paymentMethodTo.balance + editTextPriceTransfer.text.toString().replace(",",".").toFloat()
    }


    //функция закрытия активности
    private fun closeActivity(){
        this.finish()
    }
}