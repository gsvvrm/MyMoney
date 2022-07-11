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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.gsa.mymoney.R
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.viewmodel.CategoryViewModel
import com.gsa.mymoney.viewmodel.PaymentMethodViewModel
import com.gsa.mymoney.viewmodel.PurchaseViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ProfitEditor"

class ProfitEditor : AppCompatActivity() {

    private lateinit var purchase: Purchase
    private var paymentMethod: PaymentMethod = PaymentMethod()

    private val calendar: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("MM.yyyy")

    private lateinit var editTextNameProfit: EditText
    private lateinit var spinnerOptionProfitPoint: Spinner
    private lateinit var editTextPriceProfit: EditText
    private lateinit var editTextNoteProfit: EditText
    private lateinit var btnAddProfit: Button


    private val paymentMethodViewModel: PaymentMethodViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PaymentMethodViewModel::class.java)
    }

    private val purchaseViewModel: PurchaseViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PurchaseViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profit_editor)

        editTextNameProfit = findViewById(R.id.editTextNamePprofit)

        spinnerOptionProfitPoint = findViewById(R.id.spinnerOptionPointProfit)

        editTextPriceProfit = findViewById(R.id.editTextPriceProfit)

        editTextNoteProfit = findViewById(R.id.editTextNoteProfit)

        btnAddProfit = findViewById(R.id.btnAddProfit)

        spinnerOptionProfitPoint.isEnabled = true
        editTextPriceProfit.isEnabled = false
        editTextNoteProfit.isEnabled = false
        btnAddProfit.isEnabled = false

        paymentMethodViewModel.paymentMethodStringListLiveData.observe(this, androidx.lifecycle.Observer {
                paymentMethodList -> paymentMethodList

            val adapterSpinnerPayment: ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.spinner_item,paymentMethodList)

            spinnerOptionProfitPoint.adapter = adapterSpinnerPayment
        })

        buttonBlock()

        btnAddProfit.setOnClickListener { view ->
            addProfit()
            closeActivity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (paymentMethod.paymentName !== "") {
            paymentMethodViewModel.updatePaymentMethod(paymentMethod)
            Log.d(TAG, "обновление $paymentMethod")
        }

    }

    private fun buttonBlock() {

        //блок по пустому названию дохода
        editTextNameProfit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    //spinnerOptionProfitPoint.isEnabled = false
                    editTextPriceProfit.isEnabled = false
                    editTextNoteProfit.isEnabled = false

                } else if (length > 0) {
                    spinnerOptionProfitPoint.isEnabled = true
                    editTextPriceProfit.isEnabled = true
                    editTextNoteProfit.isEnabled =  true

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })

        //блок по пустой цене покупки
        editTextPriceProfit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    btnAddProfit.isEnabled = false
                    editTextNameProfit.isEnabled = true
                    spinnerOptionProfitPoint.isEnabled = true
                } else if (length > 0) {
                    btnAddProfit.isEnabled = true
                    editTextNameProfit.isEnabled = false
                    spinnerOptionProfitPoint.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })


    }


    //функция добавления дохода
    private fun addProfit(){

        purchase = Purchase(UUID.randomUUID(),
            calendar.time,editTextNameProfit.text.toString(),
            spinnerOptionProfitPoint.selectedItem.toString(),
            "",
            editTextPriceProfit.text.toString().replace(",",".").toFloat(),
            editTextNoteProfit.text.toString())

        updateBalancePayMethod()

        purchaseViewModel.addPurchase(purchase)

    }

    //обновление баланса
    private fun updateBalancePayMethod(){


        paymentMethodViewModel.loadPaymentMethodForName(purchase.payment)

        paymentMethodViewModel.paymentMethodLiveDataForName.observe(this, androidx.lifecycle.Observer {
                paymentMethod -> paymentMethod?.let {
            this.paymentMethod=paymentMethod
            Log.d(TAG, "Загрузка ${this.paymentMethod} ")
            paymentMethod.balance = newBalance()
            Log.d(TAG, "Новый баланс ${this.paymentMethod} ")

        }
        })
    }

    //функция обновления балансов
    private fun newBalance (): Float {
        val balanceNaw  = paymentMethod.balance
        val price = editTextPriceProfit.text.toString().replace(",",".").toFloat()
        return balanceNaw + price
    }

    //функция закрытия активности
    private fun closeActivity(){
        this.finish()
    }
}