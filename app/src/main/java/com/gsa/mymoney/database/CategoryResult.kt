package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CategoryResult (@PrimaryKey val id: UUID = UUID.randomUUID(),
                           var categoryName:String = "",
                           var planAbsolutValue: Float = 0f,
                           var planRelativeValue: Float = 0f,
                           var factAbsolutValue: Float = 0f,
                           var factRelativeValue: Float = 0f)