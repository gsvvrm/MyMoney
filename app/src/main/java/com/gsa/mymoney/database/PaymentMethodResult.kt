package com.gsa.mymoney.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class PaymentMethodResult (@PrimaryKey val id: UUID = UUID.randomUUID(),
                                var date:Date = Date(),
                                var paymentName:String = "",
                                var balance: Float = 0f)