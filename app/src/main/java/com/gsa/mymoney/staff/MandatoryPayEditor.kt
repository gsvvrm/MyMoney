package com.gsa.mymoney.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.gsa.mymoney.R
import com.gsa.mymoney.database.MandatoryPay
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.viewmodel.CategoryViewModel
import com.gsa.mymoney.viewmodel.MandatoryPayViewModel
import java.util.*

private const val TAG = "MandatoryPayEditor"

class MandatoryPayEditor : AppCompatActivity() {

    private lateinit var mandatoryPay: MandatoryPay

    private lateinit var editTextNameMandatoryPay: EditText
    private lateinit var spinnerOptionCategoryMandatoryPay: Spinner
    private lateinit var editTextPriceMandatoryPay: EditText
    private lateinit var editTextDayOfPay: EditText
    private lateinit var switchOnRepeatPay: Switch
    private lateinit var btnAddMandatoryPay: Button

    private val mandatoryPayViewModel: MandatoryPayViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(MandatoryPayViewModel::class.java)
    }

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(CategoryViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mandatory_pay_editor)

        editTextNameMandatoryPay = findViewById(R.id.editTextNameMandatoryPay)

        spinnerOptionCategoryMandatoryPay = findViewById(R.id.spinnerOptionCategoryMandatoryPay)

        editTextPriceMandatoryPay = findViewById(R.id.editTextPriceMandatoryPay)

        editTextDayOfPay = findViewById(R.id.editTextDayOfPay)

        switchOnRepeatPay = findViewById(R.id.switchOnRepeatPay)

        btnAddMandatoryPay = findViewById(R.id.btnAddMandatoryPay)

        btnAddMandatoryPay.isEnabled = false
        editTextPriceMandatoryPay.isEnabled = false
        editTextDayOfPay.isEnabled = false
        switchOnRepeatPay.isEnabled = false

        categoryViewModel.categoriesStringListLiveData.observe(this, Observer {
                categoryList -> categoryList

            val adapterSpinnerCategory: ArrayAdapter<String> = ArrayAdapter <String>(this,R.layout.spinner_item,categoryList)

            spinnerOptionCategoryMandatoryPay.adapter = adapterSpinnerCategory

        })

        buttonBlock()

        btnAddMandatoryPay.setOnClickListener { view ->
            addMandatoryPay()
            closeActivity()
        }


    }

    private fun buttonBlock() {

        //блок по пустому названию покупки
        editTextNameMandatoryPay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    editTextPriceMandatoryPay.isEnabled = false

                } else if (length > 0) {
                    spinnerOptionCategoryMandatoryPay.isEnabled = true
                    editTextPriceMandatoryPay.isEnabled = true


                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })

        //блок по пустой цене покупки
        editTextPriceMandatoryPay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    btnAddMandatoryPay.isEnabled = false
                    editTextNameMandatoryPay.isEnabled = true
                    spinnerOptionCategoryMandatoryPay.isEnabled = true
                    editTextDayOfPay.isEnabled = false
                    switchOnRepeatPay.isEnabled = false
                } else if (length > 0) {
                    btnAddMandatoryPay.isEnabled = true
                    editTextNameMandatoryPay.isEnabled = false
                    spinnerOptionCategoryMandatoryPay.isEnabled = false
                    editTextDayOfPay.isEnabled = true
                    switchOnRepeatPay.isEnabled = true
                    switchOnRepeatPay.isChecked = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int, ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int, ) {
            }
        })


    }

    //функция добавления платежа
    private fun addMandatoryPay(){

        mandatoryPay = MandatoryPay(
            UUID.randomUUID(),
            editTextDayOfPay.text.toString().toInt(),
            editTextNameMandatoryPay.text.toString(),
            "",
            spinnerOptionCategoryMandatoryPay.selectedItem.toString(),
            editTextPriceMandatoryPay.text.toString().replace(",",".").toFloat(),
            switchOnRepeatPay.isChecked,
            false,
            ""
            )

        mandatoryPayViewModel.addMandatoryPay(mandatoryPay)
        Log.d(TAG, "Добавление $mandatoryPay ")


    }

    //функция закрытия активности
    private fun closeActivity(){
        this.finish()
    }
}