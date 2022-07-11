package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MandatoryPay(@PrimaryKey val id: UUID = UUID.randomUUID(),
                        var dayOfMonthForPay: Int,
                        var nameBuy:String = "",
                        var payment:String = "",
                        var category:String = "",
                        var price: Float = 0f,
                        var isRepeat: Boolean = true,
                        var isPaid: Boolean = false,
                        var notePay:String = "")
