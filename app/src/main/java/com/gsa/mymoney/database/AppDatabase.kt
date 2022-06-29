package com.gsa.mymoney.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Purchase::class, PaymentMethod::class, Category::class, MandatoryPay::class], version = 1)
@TypeConverters(AppTypeConverters::class)
abstract class MoneyDatabase : RoomDatabase () {

    abstract fun purchaseDao(): PurchaseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun paymentMethodDao(): PaymentMethodDao
    abstract fun mandatoryPayDao(): MandatoryPayDao

}