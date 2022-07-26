package com.gsa.mymoney

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsa.mymoney.database.Purchase
import com.gsa.mymoney.viewmodel.PurchaseViewModel
import java.text.SimpleDateFormat

private const val TAG = "HistoryFragment"

class HistoryFragment : Fragment() {

    var sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")

    private lateinit var listDatePurchaseRecyclerView: RecyclerView

    private var dateAdapter: DateAdapter? = DateAdapter(emptyList())

    private val purchaseViewModel: PurchaseViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PurchaseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        listDatePurchaseRecyclerView = view.findViewById(R.id.datePurchaseRecyclerView)
        listDatePurchaseRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        purchaseViewModel.getDatePurchaseList().observe(viewLifecycleOwner, Observer { listDatePurchases ->
            listDatePurchases?.let {
                updateListDatePurchase(listDatePurchases)
            }

        })
    }

    //холдер для даты
    private inner class DateHolder (view: View) : RecyclerView.ViewHolder(view){

        val textViewDateComForList: TextView = itemView.findViewById(R.id.textViewDateComForList)
        val purchaseRecyclerViewFromDate: RecyclerView = itemView.findViewById(R.id.purchaseRecyclerViewFromDate)

        private var purchaseAdapter: PurchaseAdapter? = PurchaseAdapter(emptyList())

        //холдер для покупки
        private inner class PurchaseHolder (view: View) : RecyclerView.ViewHolder(view) {

            val dateBuyItem: TextView = itemView.findViewById(R.id.itemDateBuy)
            val nameBuyItem: TextView = itemView.findViewById(R.id.itemNameBuy)
            val priceItem: TextView = itemView.findViewById(R.id.itemPrice)
            val categoryItem: TextView =  itemView.findViewById(R.id.itemCategory)
            val paymentItem: TextView =  itemView.findViewById(R.id.itemPayment)
            val notePayItem: TextView =  itemView.findViewById(R.id.itemNotePay)

        }

        //адаптер для покупки
        private inner class PurchaseAdapter (var purchases: List<Purchase>) : RecyclerView.Adapter<PurchaseHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseHolder {
                val view = layoutInflater.inflate(R.layout.list_item_purchase,parent,false)



                return PurchaseHolder(view)
            }

            override fun onBindViewHolder(holder: PurchaseHolder, position: Int) {
                val purchase = purchases[position]
                holder.apply {

                    dateBuyItem.text = sdf.format(purchase.date)
                    nameBuyItem.text = purchase.nameBuy
                    priceItem.text = String.format("%.2f", purchase.price)
                    categoryItem.text = purchase.category
                    paymentItem.text = purchase.payment
                    notePayItem.text = purchase.notePay

                    dateBuyItem.isVisible = false
                    priceItem.isVisible = (purchase.price != 0f)
                    notePayItem.isVisible = (notePayItem.length() !=0)

                }
            }

            override fun getItemCount(): Int {
                return purchases.size
            }

        }

        fun updateListPurchaseUI (purchases: List<Purchase>) {
            purchaseAdapter = PurchaseAdapter(purchases)
            purchaseRecyclerViewFromDate.adapter = purchaseAdapter
        }

    }

    //адаптер для даты
    private inner class DateAdapter (var dateTypeSs: List<String>) : RecyclerView.Adapter<DateHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
            val view = layoutInflater.inflate(R.layout.list_item_date_for_list,parent,false)
            return DateHolder(view)
        }

        override fun onBindViewHolder(holder: DateHolder, position: Int) {
            val dateTypeS = dateTypeSs[position]
            holder.apply {

                textViewDateComForList.text = dateTypeS
                purchaseRecyclerViewFromDate.layoutManager = LinearLayoutManager(context)

                purchaseViewModel.getPurchasesForDate(textViewDateComForList.text.toString()).observe(viewLifecycleOwner,
                    Observer { purchases ->
                        purchases?.let {
                            updateListPurchaseUI(purchases)
                        }
                    })
            }
        }

        override fun getItemCount(): Int {
            return dateTypeSs.size
        }

    }

    private fun updateListDatePurchase (dateTypeSs: List<String>) {
        dateAdapter = DateAdapter(dateTypeSs)
        listDatePurchaseRecyclerView.adapter=dateAdapter
    }

    companion object {
    }
}