package com.gsa.mymoney.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.selects.select
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

    //запрос категорий для спинера
    @Query("select categoryName from Category")
    fun getStringCategories(): LiveData<List<String>>


    //запрос одной категории по ID
    @Query("select * from Category where id=(:id)")
    fun getCategoryForID (id: UUID) : LiveData<Category?>

    //запрос одной категории по имени и последнего созданного
    @Query("select * from Category where categoryName=(:categoryName) order by dateCreate desc limit 1")
    fun getCategoryForName (categoryName: String) : LiveData<Category?>

}