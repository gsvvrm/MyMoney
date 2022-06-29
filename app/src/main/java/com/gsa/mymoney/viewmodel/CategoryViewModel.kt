package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.Category
import java.util.*

class CategoryViewModel: ViewModel() {

    private val appDBRepository = AppDBRepository.get()
    private val categoryIdLiveData = MutableLiveData<UUID>()

    //добавление категории
    fun addCategory (category: Category) = appDBRepository.addCategory(category)

    //обновление категории
    fun updateCategory (category: Category) = appDBRepository.updateCategory(category)

    //удаление категории
    fun deleteCategory (category: Category) = appDBRepository.deleteCategory(category)

    //получение одной категории
    var categoryLiveData: LiveData<Category?> = Transformations.switchMap(categoryIdLiveData){ categoryID ->
        appDBRepository.getCategory(categoryID)
    }

    fun loadCategory (categoryID: UUID) {
        categoryIdLiveData.value = categoryID
    }

    //получение списка категории
    val categoriesListLiveData = appDBRepository.getCategories()

}