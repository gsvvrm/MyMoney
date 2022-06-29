package com.gsa.mymoney

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gsa.mymoney.staff.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var fabMain: FloatingActionButton
    private lateinit var fabAddPurchase: ExtendedFloatingActionButton
    private lateinit var fabAddProfit: ExtendedFloatingActionButton
    private lateinit var fabAddTransfer: ExtendedFloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        fabMain = findViewById(R.id.fab_main)
        fabAddPurchase = findViewById(R.id.fab_purchase)
        fabAddProfit = findViewById(R.id.fab_profit)
        fabAddTransfer = findViewById(R.id.fab_transfer)


        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.navContainerView)
        if (currentFragment == null) {
            val fragment = MineFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.navContainerView, fragment)
                .commit()
        }

        fabMain.setOnClickListener { view ->
            switchFabVisibility()
        }

        fabAddPurchase.setOnClickListener { view ->
            val intent = Intent(this, PurchaseEditor::class.java)
            startActivity(intent)
            isFabInvisible()
        }

        fabAddProfit.setOnClickListener { view ->
            val intent = Intent(this, ProfitEditor::class.java)
            startActivity(intent)
            isFabInvisible()
        }

        fabAddTransfer.setOnClickListener { view ->
            val intent = Intent(this, TransferEditor::class.java)
            startActivity(intent)
            isFabInvisible()
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_mine -> {
                    val fragment = MineFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.navContainerView, fragment).commit()
                    isFabInvisible()
                    true
                }
                R.id.page_planning -> {
                    val fragment = PlanningFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.navContainerView, fragment).commit()
                    isFabInvisible()
                    true
                }
                R.id.page_categories -> {
                    val fragment = CategoriesFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.navContainerView, fragment).commit()
                    isFabInvisible()
                    true
                }
                R.id.page_history -> {
                    val fragment = HistoryFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.navContainerView, fragment).commit()
                    isFabInvisible()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_category -> {
                val intent = Intent(this, CategoryEditor::class.java)
                startActivity(intent)
                isFabInvisible()
                true
            }
            R.id.add_payment -> {
                val intent = Intent(this, PaymentEditor::class.java)
                startActivity(intent)
                isFabInvisible()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //показ скрытие кнопок добавления покупки или дохода
    private fun switchFabVisibility () {
        if (fabAddProfit.isInvisible){
            fabAddProfit.isVisible = true
        } else if (fabAddProfit.isVisible){
            fabAddProfit.isInvisible=true
        }
        if (fabAddPurchase.isInvisible){
            fabAddPurchase.isVisible = true
        } else if (fabAddPurchase.isVisible){
            fabAddPurchase.isInvisible =true
        }
        if (fabAddTransfer.isInvisible){
            fabAddTransfer.isVisible = true
        } else if (fabAddTransfer.isVisible){
            fabAddTransfer.isInvisible =true
        }

    }

    //скрытие кнопок
    private fun isFabInvisible(){
        fabAddProfit.isInvisible=true
        fabAddPurchase.isInvisible =true
        fabAddTransfer.isInvisible =true
    }
}