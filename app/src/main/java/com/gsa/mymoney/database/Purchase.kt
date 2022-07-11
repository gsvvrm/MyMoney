package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Purchase (@PrimaryKey val id: UUID = UUID.randomUUID(),
                    var date:Date = Date(),
                    var nameBuy:String = "",
                    var payment:String = "",
                    var category:String = "",
                    var price: Float = 0f,
                    var notePay:String = "")
