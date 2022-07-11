package com.gsa.mymoney

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.viewmodel.PaymentMethodViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MineFragment"

class MineFragment : Fragment() {

    private lateinit var sumBalance: TextView
    private lateinit var paymentRecyclerView: RecyclerView
    private var adapterPayment: PaymentAdapter? = PaymentAdapter(emptyList())

    private val calendar: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("MM.yyyy")

    private val paymentMethodViewModel: PaymentMethodViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(PaymentMethodViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)

        sumBalance = view.findViewById(R.id.sumBalance)

        paymentRecyclerView = view.findViewById(R.id.paymentRecyclerViev) as RecyclerView
        paymentRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentMethodViewModel.paymentMethodListLiveData.observe(viewLifecycleOwner, Observer { paymentMethods ->
            paymentMethods?.let {
                updateListPaymentUI(paymentMethods)
            }
        })

        paymentMethodViewModel.sumBalance.observe(viewLifecycleOwner, Observer {
            summaryBalance -> summaryBalance.let {
            if (summaryBalance == null) {
                sumBalance.text = "0.0"
            } else {
                sumBalance.text = summaryBalance.toString()
            }
        }
        })

    }

    //холдер для метода
    private inner class PaymentHolder (view: View) : RecyclerView.ViewHolder(view) {

        val paymentNameItem: TextView = itemView.findViewById(R.id.itemPaymentName)
        val paymentBalanceItem: TextView = itemView.findViewById(R.id.itemPaymentBalance)
    }

    //адаптер для метода
    private inner class PaymentAdapter (var paymentMethods: List<PaymentMethod>) : RecyclerView.Adapter<PaymentHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_payment, parent, false)
            return PaymentHolder(view)
        }

        override fun onBindViewHolder(holder: PaymentHolder, position: Int) {
            val paymentMethod = paymentMethods[position]
            holder.apply {
                paymentNameItem.text = paymentMethod.paymentName
                paymentBalanceItem.text = paymentMethod.balance.toString()
            }
        }

        override fun getItemCount(): Int {
            return paymentMethods.size
        }

    }

    private fun updateListPaymentUI (paymentMethods: List<PaymentMethod>) {
        adapterPayment=PaymentAdapter(paymentMethods)
        paymentRecyclerView.adapter = adapterPayment

    }

    companion object {

    }
}