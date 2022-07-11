package com.gsa.mymoney.staff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.gsa.mymoney.R
import com.gsa.mymoney.database.Category
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.viewmodel.CategoryViewModel
import com.gsa.mymoney.viewmodel.PaymentMethodViewModel
import java.util.*

private const val TAG = "CategoryEditor"

class CategoryEditor : AppCompatActivity() {

    private lateinit var category: Category

    private lateinit var categoryNameEditText: EditText
    private lateinit var budgetAbsValue: EditText

    private lateinit var btnSaveCategory: Button

    private val calendar: Calendar = Calendar.getInstance()

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(CategoryViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_editor)

        categoryNameEditText = findViewById(R.id.editTextCategoryName)
        budgetAbsValue = findViewById(R.id.editTextBudgetAbsValue)

        btnSaveCategory = findViewById(R.id.btnSaveCategory)
        btnSaveCategory.isEnabled=false

        blockButton()

        btnSaveCategory.setOnClickListener { view ->
            addPayment()
            closeActivity()
        }

    }

    private fun addPayment() {

        category = Category(UUID.randomUUID(),
                            calendar.time,
                            categoryNameEditText.text.toString(),
                            budgetAbsValue.text.toString().replace(",",".").toFloat())

        categoryViewModel.addCategory(category)

    }

    private fun savePayment() {

    }

    private fun deletePayment() {

    }

    private fun blockButton() {
        categoryNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    budgetAbsValue.isEnabled = false
                } else if (length > 0) {
                    budgetAbsValue.isEnabled = true
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

        budgetAbsValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val length = s.length
                if (length == 0) {
                    btnSaveCategory.isEnabled = false
                    categoryNameEditText.isEnabled = true
                } else if (length > 0) {
                    btnSaveCategory.isEnabled = true
                    categoryNameEditText.isEnabled = false
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