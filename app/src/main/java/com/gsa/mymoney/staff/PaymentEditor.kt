package com.gsa.mymoney.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.gsa.mymoney.R
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.viewmodel.PaymentMethodViewModel
import java.util.*

private const val TAG = "PaymentEditor"

class PaymentEditor : AppCompatActivity() {

    private lateinit var paymentMethod: PaymentMethod

    private lateinit var payNameEditText : EditText
    private lateinit var btnSavePayment : Button

    private val calendar: Calendar = Calendar.getInstance()

    private val paymentMethodViewModel: PaymentMethodViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PaymentMethodViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_editor)

        payNameEditText = findViewById(R.id.payNameEditText)

        btnSavePayment = findViewById(R.id.btnSavePayment)
        btnSavePayment.isEnabled=false

        blockButton()

        btnSavePayment.setOnClickListener { view ->
            addPayment()
            closeActivity()
        }


    }

    private fun addPayment() {
        paymentMethod = PaymentMethod(UUID.randomUUID(),
            calendar.time,
            payNameEditText.text.toString(),
            0.0F)

        paymentMethodViewModel.addPaymentMethod(paymentMethod)

    }

    private fun savePayment() {


    }

    private fun deletePayment() {


    }

    private fun blockButton() {
        payNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    btnSavePayment.isEnabled = false
                } else if (length > 0) {
                    btnSavePayment.isEnabled = true
                }

            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {

            }
        })
    }

    private fun closeActivity(){
        this.finish()
    }

}