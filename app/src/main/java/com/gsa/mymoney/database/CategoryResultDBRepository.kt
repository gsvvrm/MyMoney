package com.gsa.mymoney.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "CategoryResultDBRepository"
private const val DATABASE_NAME = "money-database"

class CategoryResultDBRepository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val executor = Executors.newSingleThreadExecutor()

    //Категории итоговые
    private val categoryResultDao = database.categoryResultDao()
    //добавление категории
    fun addCategoryResult (categoryResult: CategoryResult){
        executor.execute { categoryResultDao.addCategoryResult(categoryResult) }
    }
    //изменение категории
    fun updateCategoryResult (categoryResult: CategoryResult) {
        executor.execute { categoryResultDao.updateCategoryResult(categoryResult) }
    }
    //удаление категории
    fun deleteCategoryResult (categoryResult: CategoryResult) {
        executor.execute { categoryResultDao.deleteCategoryResult(categoryResult) }
    }
    //запрос списка всех категорий
    fun getCategoriesResults(): LiveData<List<CategoryResult>> = categoryResultDao.getCategoriesResults()
    //запрос списка всех категорий для спинера
    fun getStringCategoriesResults(): LiveData<List<String>> = categoryResultDao.getStringCategoriesResults()
    //запрос одной категории
    fun getCategoryResult(id: UUID): LiveData<CategoryResult?> = categoryResultDao.getCategoryResult(id)


    companion object{
        private var INSTANCE: CategoryResultDBRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CategoryResultDBRepository(context)
                Log.d(TAG,"CategoryResultDBRepository is initialized")

            }

        }

        fun get(): CategoryResultDBRepository{
            return INSTANCE ?:
            throw IllegalStateException ("CategoryResultDBRepository must be initialized")
        }
    }
}