package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "CategoryDBRepository"
private const val DATABASE_NAME = "money-database"

class CategoryDBRepository private constructor(context: Context)  {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()

    //Категории
    private val categoryDao = database.categoryDao()
    //добавление категории
    fun addCategory (category: Category){
        executor.execute { categoryDao.addCategory(category) }
    }
    //изменение категории
    fun updateCategory (category: Category) {
        executor.execute { categoryDao.updateCategory(category) }
    }
    //удаление категории
    fun deleteCategory (category: Category) {
        executor.execute { categoryDao.deleteCategory(category) }
    }
    //запрос списка всех категорий
    fun getCategories(): LiveData<List<Category>> = categoryDao.getCategories()
    //запрос списка всех категорий для спинера
    fun getStringCategories(): LiveData<List<String>> = categoryDao.getStringCategories()
    //запрос одной категории по ID
    fun getCategoryForID(id: UUID): LiveData<Category?> = categoryDao.getCategoryForID(id)
    //запрос одной категории по имени
    fun getCategoryForName(categoryName: String): LiveData<Category?> = categoryDao.getCategoryForName(categoryName)

    companion object{
        private var INSTANCE: CategoryDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CategoryDBRepository(context)
                Log.d(TAG,"CategoryDBRepository is initialized")

            }

        }

        fun get(): CategoryDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("CategoryDBRepository must be initialized")
        }
    }
}