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
import androidx.lifecycle.get
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsa.mymoney.database.MandatoryPay
import com.gsa.mymoney.viewmodel.MandatoryPayViewModel

private const val TAG = "PlanningFragment"

class PlanningFragment : Fragment() {

    private lateinit var mandatoryRecyclerView: RecyclerView

    private var mandatoryPayAdapter: MandatoryPayAdapter? = MandatoryPayAdapter(emptyList())

    private val mandatoryPayViewModel: MandatoryPayViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(MandatoryPayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_planning, container, false)

        mandatoryRecyclerView = view.findViewById(R.id.mandatoryRecyclerView)
        mandatoryRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mandatoryPayViewModel.mandatoryPaisListLiveData.observe(viewLifecycleOwner, Observer { manndatoryPays ->
            manndatoryPays?.let {
                updateListMandatoryPayUI(manndatoryPays)
            }
        })


    }

    //холдер для планированного платежа
    private inner class MandatoryPayHolder (view: View) : RecyclerView.ViewHolder (view) {

        val nameMandatoryPayItem: TextView = itemView.findViewById(R.id.itemNameMandatoryPay)
        val categoryMandatoryPayItem: TextView = itemView.findViewById(R.id.itemCategoryMandatoryPay)
        val priceMandatoryPayItem: TextView = itemView.findViewById(R.id.itemPriceMandatoryPay)
        val noteMandatoryPayItem: TextView = itemView.findViewById(R.id.itemNoteMandatoryPay)
        val repeatMandatoryPayItem: TextView = itemView.findViewById(R.id.itemRepeatMandatoryPay)
    }

    //адаптер для планированного платежа
    private inner class MandatoryPayAdapter (var mandatoryPays: List<MandatoryPay>) : RecyclerView.Adapter<MandatoryPayHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandatoryPayHolder {
            val view = layoutInflater.inflate(R.layout.list_item_mandatory_pay,parent,false)
            return MandatoryPayHolder(view)
        }

        override fun onBindViewHolder(holder: MandatoryPayHolder, position: Int) {
            val mandatoryPay = mandatoryPays[position]
            holder.apply {
                nameMandatoryPayItem.text = mandatoryPay.nameBuy
                categoryMandatoryPayItem.text = mandatoryPay.category
                priceMandatoryPayItem.text = String.format("%.2f", mandatoryPay.price)
                noteMandatoryPayItem.text = mandatoryPay.notePay
                noteMandatoryPayItem.isVisible = (nameMandatoryPayItem.length() !=0)
                repeatMandatoryPayItem.text = mandatoryPay.isRepeat.toString()
            }
        }

        override fun getItemCount(): Int {
            return mandatoryPays.size
        }

    }

    private fun updateListMandatoryPayUI(mandatoryPays: List<MandatoryPay>){
        mandatoryPayAdapter = MandatoryPayAdapter(mandatoryPays)
        mandatoryRecyclerView.adapter = mandatoryPayAdapter

    }

    companion object {

    }
}