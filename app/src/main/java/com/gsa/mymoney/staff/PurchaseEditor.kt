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
import androidx.lifecycle.observe
import com.gsa.mymoney.R
import com.gsa.mymoney.database.Category
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.viewmodel.CategoryViewModel
import com.gsa.mymoney.viewmodel.PaymentMethodViewModel
import com.gsa.mymoney.viewmodel.PurchaseViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PurchaseEditor"

class PurchaseEditor : AppCompatActivity() {

    private lateinit var purchase: Purchase
    private var paymentMethod: PaymentMethod = PaymentMethod()

    private val calendar: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    private lateinit var editTextNamePurchase: EditText
    private lateinit var spinnerOptionCategory: Spinner
    private lateinit var spinnerOptionPay: Spinner
    private lateinit var editTextPricePurchase: EditText
    private lateinit var editTextNotePay: EditText
    private lateinit var btnAddPurchase: Button


    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(CategoryViewModel::class.java)
    }

    private val paymentMethodViewModel: PaymentMethodViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PaymentMethodViewModel::class.java)
    }

    private val purchaseViewModel: PurchaseViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PurchaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_editor)

        Log.d(TAG, "день ${calendar.get(Calendar.DAY_OF_MONTH)} месяц ${calendar.get(Calendar.MONTH)}")

        editTextNamePurchase = findViewById(R.id.editTextNamePurchase)

        spinnerOptionCategory = findViewById(R.id.spinnerOptionCategory)

        spinnerOptionPay = findViewById(R.id.spinnerOptionPay)

        editTextPricePurchase = findViewById(R.id.editTextPricePurchase)

        editTextNotePay = findViewById(R.id.editTextNotePay)

        btnAddPurchase = findViewById(R.id.btnAddPurchase)

        btnAddPurchase.isEnabled = false
        spinnerOptionCategory.isEnabled = true
        spinnerOptionPay.isEnabled = true
        editTextPricePurchase.isEnabled = false
        editTextNotePay.isEnabled = false

        categoryViewModel.categoriesStringListLiveData.observe(this, Observer {
            categoryList -> categoryList

            val adapterSpinnerCategory: ArrayAdapter<String> = ArrayAdapter <String>(this,R.layout.spinner_item,categoryList)

            spinnerOptionCategory.adapter = adapterSpinnerCategory

        })

        paymentMethodViewModel.paymentMethodStringListLiveData.observe(this, Observer {
            paymentMethodList -> paymentMethodList

            val adapterSpinnerPayment: ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.spinner_item,paymentMethodList)

            spinnerOptionPay.adapter = adapterSpinnerPayment

        })

        buttonBlock()

        btnAddPurchase.setOnClickListener { view ->
            addPurchase()
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

        //блок по пустому названию покупки
        editTextNamePurchase.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
//                    spinnerOptionCategory.isEnabled = false
//                    spinnerOptionPay.isEnabled = false
                    editTextPricePurchase.isEnabled = false
                    editTextNotePay.isEnabled = false

                } else if (length > 0) {
                    spinnerOptionCategory.isEnabled = true
                    spinnerOptionPay.isEnabled = true
                    editTextPricePurchase.isEnabled = true
                    editTextNotePay.isEnabled =  true

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })

        //блок по пустой цене покупки
        editTextPricePurchase.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    btnAddPurchase.isEnabled = false
                    editTextNamePurchase.isEnabled = true
                    spinnerOptionCategory.isEnabled = true
                    spinnerOptionPay.isEnabled = true
                } else if (length > 0) {
                    btnAddPurchase.isEnabled = true
                    editTextNamePurchase.isEnabled = false
                    spinnerOptionCategory.isEnabled = false
                    spinnerOptionPay.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })


    }

    //функция добавления покупки
    private fun addPurchase(){

        purchase = Purchase(UUID.randomUUID(),
            calendar.time,
            editTextNamePurchase.text.toString(),
            spinnerOptionPay.selectedItem.toString(),
            spinnerOptionCategory.selectedItem.toString(),
            -editTextPricePurchase.text.toString().replace(",",".").toFloat(),
            editTextNotePay.text.toString(),
            sdf.format(calendar.time).toString())

        updateBalancePayMethod()

        purchaseViewModel.addPurchase(purchase)
        Log.d(TAG, "Добавление ${purchase.nameBuy} ")


    }

    //обновление баланса
    private fun updateBalancePayMethod(){


        paymentMethodViewModel.loadPaymentMethodForName(purchase.payment)

        paymentMethodViewModel.paymentMethodLiveDataForName.observe(this, Observer {
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
        val price = editTextPricePurchase.text.toString().replace(",",".").toFloat()
        return balanceNaw - price
    }

    //функция закрытия активности
    private fun closeActivity(){
        this.finish()
    }
}