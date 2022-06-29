package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Category(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var categoryIndex:String,
                    var indexDescription:String,
                    var planAbsolutValue: Float,
                    var planRelativeValue: Float,
                    var factAbsolutValue: Float,
                    var factRelativeValue: Float)
