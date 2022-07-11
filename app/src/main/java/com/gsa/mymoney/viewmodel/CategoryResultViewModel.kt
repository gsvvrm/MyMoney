package com.gsa.mymoney.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gsa.mymoney.database.AppDBRepository
import com.gsa.mymoney.database.Category
import com.gsa.mymoney.database.CategoryResult
import com.gsa.mymoney.database.CategoryResultDBRepository
import java.util.*

class CategoryResultViewModel: ViewModel() {

    private val categoryResultDBRepository = CategoryResultDBRepository.get()
    private val categoryResultIdLiveData = MutableLiveData<UUID>()

    //добавление категории
    fun addCategoryResult (categoryResult: CategoryResult) = categoryResultDBRepository.addCategoryResult(categoryResult)

    //обновление категории
    fun updateCategoryResult (categoryResult: CategoryResult) = categoryResultDBRepository.updateCategoryResult(categoryResult)

    //удаление категории
    fun deleteCategoryResult (categoryResult: CategoryResult) = categoryResultDBRepository.deleteCategoryResult(categoryResult)

    //получение одной категории
    var categoryResultLiveData: LiveData<CategoryResult?> = Transformations.switchMap(categoryResultIdLiveData){ categoryResultID ->
        categoryResultDBRepository.getCategoryResult(categoryResultID)
    }

    fun loadCategoryResult (categoryResultID: UUID) {
        categoryResultIdLiveData.value = categoryResultID
    }

    //получение списка категории
    val categoriesResultsListLiveData = categoryResultDBRepository.getCategoriesResults()
    //получение списка категорий для спинера
    val categoriesResultsStringListLiveData = categoryResultDBRepository.getStringCategoriesResults()
}