package com.gsa.mymoney

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gsa.mymoney.database.Category
import com.gsa.mymoney.database.PaymentMethod
import com.gsa.mymoney.viewmodel.CategoryViewModel
import com.gsa.mymoney.viewmodel.PurchaseViewModel

private const val TAG = "CategoriesFragment"

class CategoriesFragment : Fragment() {

    private lateinit var categoryRecyclerView: RecyclerView
    private var adapterCategory: CategoryAdapter? = CategoryAdapter(emptyList())


    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProvider (this, defaultViewModelProviderFactory).get(CategoryViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView) as RecyclerView
        categoryRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.categoriesListLiveData.observe(viewLifecycleOwner, Observer { categories ->
            categories?.let {
                updateListCategoryUI(categories)
            }
        })
    }

    //холдер для категории
    private inner class CategoryHolder (view: View) : RecyclerView.ViewHolder(view) {

        val categoryNameItem: TextView = itemView.findViewById(R.id.itemCategoryName)
        val planAbsolutValueItem: TextView = itemView.findViewById(R.id.itemPlanAbsolutValue)
        val factAbsolutValueItem: TextView = itemView.findViewById(R.id.itemFactAbsolutValue)
        val factRelativeValueItem: TextView = itemView.findViewById(R.id.itemFactRelativeValue)

    }

    //адаптер для категории
    private inner class CategoryAdapter (var categories: List<Category>) : RecyclerView.Adapter<CategoryHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
            val view = layoutInflater.inflate(R.layout.list_item_category, parent, false)
            return CategoryHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
            val category = categories[position]
            holder.apply {
                categoryNameItem.text = category.categoryName
                planAbsolutValueItem.text = category.planAbsolutValue.toString()
                factAbsolutValueItem.text = category.factAbsolutValue.toString()
                factRelativeValueItem.text = category.factRelativeValue.toString()
            }
        }

        override fun getItemCount(): Int {
            return categories.size
        }

    }

    private fun updateListCategoryUI (categories: List<Category>) {
        adapterCategory=CategoryAdapter(categories)
        categoryRecyclerView.adapter = adapterCategory

    }

    companion object {

    }
}