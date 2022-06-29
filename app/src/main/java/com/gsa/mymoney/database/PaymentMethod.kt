package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class PaymentMethod(@PrimaryKey val id: UUID = UUID.randomUUID(),
                         var payment:String,
                         var paymentDescription:String)
