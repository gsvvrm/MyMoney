package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.Category
import com.gsa.mymoney.database.CategoryDBRepository
import java.util.*

class CategoryViewModel: ViewModel() {

    private val categoryDBRepository = CategoryDBRepository.get()
    private val categoryIdLiveData = MutableLiveData<UUID>()
    private val categoryNameLiveData = MutableLiveData<String>()

    //добавление категории
    fun addCategory (category: Category) = categoryDBRepository.addCategory(category)

    //обновление категории
    fun updateCategory (category: Category) = categoryDBRepository.updateCategory(category)

    //удаление категории
    fun deleteCategory (category: Category) = categoryDBRepository.deleteCategory(category)

    //получение одной категории по ID
    var categoryLiveDataForID: LiveData<Category?> = Transformations.switchMap(categoryIdLiveData){ categoryID ->
        categoryDBRepository.getCategoryForID(categoryID)
    }

    fun loadCategoryForID (categoryID: UUID) {
        categoryIdLiveData.value = categoryID
    }

    //получение одной категории по имени
    var categoryLiveDataForName: LiveData<Category?> = Transformations.switchMap(categoryNameLiveData){ categoryName ->
        categoryDBRepository.getCategoryForName(categoryName)
    }

    fun loadCategoryForName (categoryName: String) {
        categoryNameLiveData.value = categoryName
    }



    //получение списка категории
    val categoriesListLiveData = categoryDBRepository.getCategories()
    //получение списка категорий для спинера
    val categoriesStringListLiveData = categoryDBRepository.getStringCategories()

}