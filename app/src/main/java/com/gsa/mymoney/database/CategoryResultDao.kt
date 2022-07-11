package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface CategoryResultDao {

    @Insert
    fun addCategoryResult (categoryResult: CategoryResult)

    @Update
    fun updateCategoryResult (categoryResult: CategoryResult)

    @Delete
    fun deleteCategoryResult (categoryResult: CategoryResult)

    //запрос всех категорий
    @Query("select * from CategoryResult")
    fun getCategoriesResults() : LiveData<List<CategoryResult>>

    //запрос категорий для спинера
    @Query("select categoryName from CategoryResult")
    fun getStringCategoriesResults(): LiveData<List<String>>


    //запрос одной категории
    @Query("select * from CategoryResult where id=(:id)")
    fun getCategoryResult (id: UUID) : LiveData<CategoryResult?>
}