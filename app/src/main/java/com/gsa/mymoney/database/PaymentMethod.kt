package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class PaymentMethod(@PrimaryKey val id: UUID = UUID.randomUUID(),
                         var dateCreate: Date = Date(),
                         var paymentName:String = "",
                         var balance: Float = 0f
                         )
