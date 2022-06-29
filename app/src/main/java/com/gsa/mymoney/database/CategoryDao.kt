package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface CategoryDao {

    @Insert
    fun addCategory (category: Category)

    @Update
    fun updateCategory (category: Category)

    @Delete
    fun deleteCategory (category: Category)

    //запрос всех категорий
    @Query("select * from Category")
    fun getCategories() : LiveData<List<Category>>

    //запрос одной категории
    @Query("select * from Category where id=(:id)")
    fun getCategory (id: UUID) : LiveData<Category?>

}