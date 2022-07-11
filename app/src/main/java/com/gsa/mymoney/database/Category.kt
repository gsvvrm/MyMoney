package com.gsa.mymoney.database

import android.widget.EditText
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity
data class Category(@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var dateCreate: Date = Date(),
                    var categoryName:String = "",
                    var planAbsolutValue: Float = 0f,
                    var planRelativeValue: Float = 0f,
                    var factAbsolutValue: Float = 0f,
                    var factRelativeValue: Float = 0f)
